package com.uilover.project252.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uilover.project252.Activity.DetailActivity
import com.uilover.project252.Domain.ItemModel
import com.uilover.project252.databinding.ViewholderSpecialBinding

class SpecialAdapter(
    private val items: MutableList<ItemModel>
) : RecyclerView.Adapter<SpecialAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderSpecialBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderSpecialBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val ctx = holder.itemView.context

        // Title
        holder.binding.titleTxt.text = item.title
        holder.binding.shortDescTxt.text = item.shortdesc

        // (Optional) Short description — only if your layout has this TextView
        // holder.binding.shortDescTxt.text = item.shortDesc

        // Image from drawable resource
        Glide.with(holder.itemView)
            .load(item.imageRes)
            .into(holder.binding.picMain)

        // Click → open detail
        holder.itemView.setOnClickListener {
            val adapterPos = holder.bindingAdapterPosition
            if (adapterPos == RecyclerView.NO_POSITION) return@setOnClickListener

            val clicked = items[adapterPos]
            val intent = Intent(ctx, DetailActivity::class.java).apply {
                putExtra("object", clicked) // Ensure ItemModel : Serializable (or Parcelable)
            }
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}