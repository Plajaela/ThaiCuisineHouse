package com.uilover.project252.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uilover.project252.Adapter.CategoryAdapter
import com.uilover.project252.Adapter.PopularAdapter
import com.uilover.project252.Adapter.SpecialAdapter
import com.uilover.project252.Domain.ItemModel
import com.uilover.project252.R
import com.uilover.project252.Repository.MainRepository
import com.uilover.project252.ViewModel.MainViewModel
import com.uilover.project252.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()
    private lateinit var allItems: List<ItemModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init data ที่ต้องพึ่ง context หลัง setContentView
        allItems = MainRepository().getAllItems()

        // ใช้ binding แทน findViewById
        binding.searchEditText.imeOptions = EditorInfo.IME_ACTION_SEARCH
        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            val isSearch = actionId == EditorInfo.IME_ACTION_SEARCH
            val isEnter = event?.keyCode == android.view.KeyEvent.KEYCODE_ENTER &&
                    event.action == android.view.KeyEvent.ACTION_DOWN
            if (isSearch || isEnter) {
                val query = binding.searchEditText.text.toString().trim()
                if (query.isNotEmpty()) searchAndNavigate(query)
                v.clearFocus()
                true
            } else false
        }

        initCategory()
        initPopular()
        initSpecial()
        initBottomMenu()

    }



    private fun initBottomMenu() {
        binding.favBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }


    private fun initSpecial() {
        binding.progressBarSpecial.visibility = View.VISIBLE
        viewModel.loadSpecial().observeForever {
            binding.recyclerViewSpecial.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.progressBarSpecial.visibility = View.GONE
            binding.recyclerViewSpecial.adapter = SpecialAdapter(it)
        }
        viewModel.loadSpecial()
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.loadPopular().observeForever {
            binding.recyclerViewPopular.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.progressBarPopular.visibility = View.GONE
            binding.recyclerViewPopular.adapter = PopularAdapter(it)
        }

    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE

        if (binding.recyclerViewCategory.layoutManager == null) {
            binding.recyclerViewCategory.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewCategory.setHasFixedSize(true)
        }

        viewModel.loadCategory().observe(this) { list ->
            binding.recyclerViewCategory.adapter = CategoryAdapter(list.toMutableList())
            binding.progressBarCategory.visibility = View.GONE
        }


    }

    private fun norm(s: String) =
        s.lowercase().replace("\\s+".toRegex(), " ").trim()

    private fun searchAndNavigate(query: String) {
        val nq = norm(query)
        val target = allItems.firstOrNull { norm(it.title) == nq }
            ?: allItems.firstOrNull { norm(it.title).contains(nq) }

        if (target != null) {
            startActivity(Intent(this, DetailActivity::class.java).apply {
                putExtra("object", target) // ต้องเป็น Parcelable หรือ Serializable
            })
        } else {
            startActivity(Intent(this, NotFoundActivity::class.java).apply {
                putExtra("query", query)
            })
        }
    }
}