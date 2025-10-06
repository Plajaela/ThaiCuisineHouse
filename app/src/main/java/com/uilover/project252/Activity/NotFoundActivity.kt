package com.uilover.project252.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.uilover.project252.R
import com.uilover.project252.databinding.ActivityNotFoundBinding

class NotFoundActivity : AppCompatActivity() {
    // Declare binding variable at the class level
    private lateinit var binding: ActivityNotFoundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Call this before setContentView

        // Initialize binding
        binding = ActivityNotFoundBinding.inflate(layoutInflater)
        // Set the content view using the binding's root
        setContentView(binding.root)

        val q = intent.getStringExtra("query").orEmpty()
        binding.message.text = "No menu named \"$q\" was found in the app. Please try spelling it again or browse the categories above."
    }
}