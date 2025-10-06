package com.uilover.project252.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uilover.project252.Activity.DetailActivity
import com.uilover.project252.Domain.ItemModel
import com.uilover.project252.Helper.FavoriteStore
import com.uilover.project252.databinding.ViewholderPopularBinding

class PopularAdapter(
    private val items: MutableList<ItemModel>
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderPopularBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderPopularBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val ctx = holder.itemView.context

        // แสดงชื่อเมนู
        holder.binding.titleTxt.text = item.title
        holder.binding.shortDescTxt.text = item.shortdesc

        // โหลดรูปจาก drawable resource (imageRes: Int)
        Glide.with(ctx)
            .load(item.imageRes)
            .into(holder.binding.pic)

        // เมื่อกด → ไปหน้า DetailActivity
        holder.itemView.setOnClickListener {
            val adapterPos = holder.bindingAdapterPosition
            if (adapterPos == RecyclerView.NO_POSITION) return@setOnClickListener

            val clicked = items[adapterPos]
            val intent = Intent(ctx, DetailActivity::class.java).apply {
                putExtra("object", clicked)  // ItemModel : Serializable
            }
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}