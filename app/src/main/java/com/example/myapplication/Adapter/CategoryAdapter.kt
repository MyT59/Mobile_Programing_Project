package com.example.myapplication.Adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.icu.text.Transliterator.Position
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activity.ListItemsActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewholderCategoryBinding
import com.example.myapplication.viewModel.CategoryModel

class CategoryAdapter(val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleTxt.text = item.title

        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .into(holder.binding.pic)

        // Update UI based on selection
        if (selectedPosition == position) {
            holder.binding.pic.setBackgroundResource(0)
            holder.binding.mainLayout.setBackgroundResource(R.drawable.lavender_bttn_background)
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.white)
                )
            )
            holder.binding.titleTxt.visibility = View.VISIBLE
            holder.binding.titleTxt.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.white)
            )
        } else {
            holder.binding.pic.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.mainLayout.setBackgroundResource(0)
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.black)
                )
            )
            holder.binding.titleTxt.visibility = View.GONE
            holder.binding.titleTxt.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.black)
            )
        }

        // Set click listener
        holder.binding.root.setOnClickListener {
            val position = position
            if (position != RecyclerView.NO_POSITION) {
                lastSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(holder.itemView.context, ListItemsActivity::class.java).apply {
                    putExtra("id", item.id.toString())
                    putExtra("title", item.title)
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }, 1000)
        }
    }


    override fun getItemCount(): Int = items.size
}
