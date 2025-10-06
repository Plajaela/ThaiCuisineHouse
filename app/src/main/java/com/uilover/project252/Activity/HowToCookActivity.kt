package com.uilover.project252.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uilover.project252.BuildConfig
import com.uilover.project252.databinding.ActivityHowToCookBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

class HowToCookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHowToCookBinding

    // ✅ ตั้ง timeout ให้ชัดเจน
    private val client by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private val jsonMT by lazy { "application/json; charset=utf-8".toMediaType() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHowToCookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getStringExtra("menu_name").orEmpty()
        val ctx  = intent.getStringExtra("menu_context").orEmpty()

        binding.titleText.text = "How to Cook : $dish"
        binding.backBtn.setOnClickListener { finish() }

        generateHowToCook(dish, ctx)
    }

    private fun generateHowToCook(dish: String, context: String) {
        binding.progress.visibility = android.view.View.VISIBLE
        binding.resultText.text = "Generating recipe for $dish ..."

        lifecycleScope.launch(Dispatchers.IO) {
            val url = BuildConfig.GEMINI_BASE_URL.trimEnd('/') +
                    "/${BuildConfig.GEMINI_MODEL}:generateContent?key=${BuildConfig.GEMINI_API_KEY}"

            val body = """
                {
                  "system_instruction": {
                    "role": "system",
                    "parts": [{
                      "text": "You are a Thai chef. Format the recipe into two main sections: Ingredients (bullet points) and Method (numbered steps 1., 2., …). Quantities should be for 1 serving. Avoid long introductions."
                    }]
                  },
                  "contents": [{
                    "role": "user",
                    "parts": [{
                      "text": "Write a recipe for: $dish\n\nAdditional context: ${context.take(300)}"
                    }]
                  }]
                }
            """.trimIndent().toRequestBody(jsonMT)

            var attempt = 0
            var text = ""
            while (attempt < 2 && text.isBlank()) {
                try {
                    val req = Request.Builder().url(url).post(body).build()
                    client.newCall(req).execute().use { resp ->
                        val respStr = resp.body?.string().orEmpty()
                        text = parseGeminiText(respStr)
                    }
                } catch (e: SocketTimeoutException) {
                    attempt++
                    delay(1000L * attempt) // backoff: 1s, 2s
                } catch (e: Exception) {
                    text = "Error: ${e.localizedMessage}"
                    break
                }
                attempt++
            }

            if (text.isBlank()) {
                text = "No response from the model. Please try again later."
            }

            withContext(Dispatchers.Main) {
                binding.progress.visibility = android.view.View.GONE
                binding.resultText.text = text
            }
        }
    }

    private fun parseGeminiText(resp: String): String {
        return try {
            val root = JSONObject(resp)
            val cand0 = root.optJSONArray("candidates")?.optJSONObject(0) ?: return ""
            val content = cand0.optJSONObject("content") ?: return ""
            val parts = content.optJSONArray("parts") ?: return ""
            parts.optJSONObject(0)?.optString("text").orEmpty()
        } catch (_: Exception) { "" }
    }
}
