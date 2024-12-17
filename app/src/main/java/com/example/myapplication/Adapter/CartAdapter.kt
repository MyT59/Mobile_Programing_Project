package com.example.myapplication.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.databinding.ViewholderCartBinding
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart

class CartAdapter(
    private val listItemsSelected: ArrayList<ItemsModel>,
    context: Context,
    private val changeNumberItemsListener: ChangeNumberItemsListener // Pass listener from constructor
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val managementCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItemsSelected[position]

        holder.binding.titleTxt.text = item.title
        holder.binding.feeEachTime.text = "$${item.price}"
        holder.binding.totalEachItem.text = "$${Math.round(item.numberInCart * item.price)}"
        holder.binding.numberItemTxt.text = item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .into(holder.binding.pic)

        holder.binding.plusCartBtn.setOnClickListener {
            managementCart.plusItem(listItemsSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() { // Correct the method name here
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }
            })
        }

        holder.binding.minusCartBtn.setOnClickListener {
            managementCart.minusItem(listItemsSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() { // Correct the method name here
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int = listItemsSelected.size
}
