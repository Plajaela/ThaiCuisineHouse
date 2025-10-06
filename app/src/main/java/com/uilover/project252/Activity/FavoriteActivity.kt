package com.uilover.project252.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uilover.project252.Adapter.SpecialAdapter
import com.uilover.project252.Helper.FavoriteStore
import com.uilover.project252.Repository.MainRepository
import com.uilover.project252.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val repo by lazy { MainRepository() }
    private var adapter: SpecialAdapter? = null   // เปลี่ยนเป็น PopularAdapter ได้

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.backBtn.setOnClickListener { finish() }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        // เผื่อผู้ใช้กดหัวใจเพิ่ม/ลบที่หน้าอื่นแล้วกลับมา
        loadData()
    }

    private fun loadData() {
        val ids = FavoriteStore.getAll(this)
            .mapNotNull { it.toIntOrNull() }
            .toSet()
        android.util.Log.d("FAV", "FavoriteActivity ids=$ids")

        val list = repo.getItemsByIds(ids)
        android.util.Log.d("FAV", "FavoriteActivity items size=${list.size}")

        binding.recyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this@FavoriteActivity)
        binding.recyclerView.adapter = com.uilover.project252.Adapter.SpecialAdapter(list)

        binding.emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}
