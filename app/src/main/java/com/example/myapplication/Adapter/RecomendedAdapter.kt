package com.example.myapplication.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activity.DetailActivity
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.databinding.ViewholderRecommendedBinding

class RecomendedAdapter(val items: MutableList<ItemsModel>) : RecyclerView.Adapter<RecomendedAdapter.Viewholder>() {
    class Viewholder(val binding: ViewholderRecommendedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecomendedAdapter.Viewholder {
        val binding = ViewholderRecommendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: RecomendedAdapter.Viewholder, position: Int) {
        val item = items[position]

        with(holder.binding) {
            titleTxt.text = item.title
            priceTxt.text = "$${item.price}"
            ratingTxt.text = item.rating.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .into(pic)

            root.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                    putExtra("object", item)
                }

                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
