package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ViewholderPicBinding

class PicAdapter(
    val items: MutableList<String>,
    private val onImageSelected: (String) -> Unit
) : RecyclerView.Adapter<PicAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class ViewHolder(val binding: ViewholderPicBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicAdapter.ViewHolder {
        val binding =
            ViewholderPicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PicAdapter.ViewHolder, position: Int) {
        val item = items[holder.adapterPosition]  // Use adapterPosition instead of position
        holder.binding.pic.loadImage(item)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position  // Use adapterPosition here as well
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            onImageSelected(item)
        }

        if (selectedPosition == holder.adapterPosition) {
            holder.binding.picLayout.setBackgroundResource(R.drawable.lavender_bg_selected)
        } else {
            holder.binding.picLayout.setBackgroundResource(R.drawable.grey_bg)
        }
    }


    private fun ImageView.loadImage(url: String) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}
