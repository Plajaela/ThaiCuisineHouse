package com.uilover.project252.Activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.uilover.project252.Adapter.SizeAdapter
import com.uilover.project252.Domain.ItemModel
import com.uilover.project252.R
import com.uilover.project252.databinding.ActivityDetailBinding
import eightbitlab.com.blurview.RenderScriptBlur
import com.uilover.project252.Helper.FavoriteStore
import android.content.Intent

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle()                 // load + bind item to views
        renderFavoriteIcon()     // show current heart state
        initFavoriteToggle()     // toggle on click
        setBlurEffect()

        binding.backBtn.setOnClickListener { finish() }

        binding.howToCookBtn.setOnClickListener {
            val intent = Intent(this@DetailActivity, HowToCookActivity::class.java).apply {
                putExtra("menu_name", item.title)        // e.g. "Gaeng Hang Lay"
                putExtra("menu_context", item.longdesc)  // คำอธิบายยาว (ถ้ามี)
            }
            startActivity(intent)
        }
    }

    /** Load ItemModel from Intent + bind to views */
    private fun bundle() = with(binding) {
        item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("object", ItemModel::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("object") as ItemModel
        }

        Glide.with(this@DetailActivity)
            .load(item.imageRes)          // drawable resource
            .into(picMain)

        titleTxt.text = item.title
        descriptionTxt.text = item.longdesc
        extraTxt.text = item.shortdesc
    }

    /** Toggle favourite + toast + refresh icon */
    private fun initFavoriteToggle() {
        binding.favBtn.setOnClickListener {
            FavoriteStore.toggle(this@DetailActivity, item.id)
            val nowFav = FavoriteStore.isFavorite(this@DetailActivity, item.id)
            renderFavoriteIcon()
            android.widget.Toast.makeText(
                this@DetailActivity,
                if (nowFav) "Added to favourites" else "Removed from favourites",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    /** Update heart icon according to current state */
    private fun renderFavoriteIcon() {
        val isFav = FavoriteStore.isFavorite(this@DetailActivity, item.id)
        // Change to your actual drawables:
        //  - R.drawable.btn_3       = empty heart
        //  - R.drawable.filledheart = full heart
        binding.favBtn.setImageResource(
            if (isFav) R.drawable.filledheart else R.drawable.btn_3
        )
    }

    /** Optional blur effect (your existing code) */
    private fun setBlurEffect() {
        val radius = 10f
        val decorView = window.decorView
        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBg = decorView.background
        binding.blurView.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowBg)
            .setBlurRadius(radius)

        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND)
        binding.blurView.setClipToOutline(true)
    }
}

