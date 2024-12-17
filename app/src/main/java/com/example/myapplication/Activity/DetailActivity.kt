package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.Adapter.PicAdapter
import com.example.myapplication.Adapter.SelectModelAdapter
import com.example.myapplication.Model.ItemsModel
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.R
import com.example.project1762.Helper.ManagmentCart

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getBundle()
        initList()

    }

    private fun initList() {
        // Populate model list
        val modelList = ArrayList<String>()
        for (models in item.model) {
            modelList.add(models)
        }

        binding.modelList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.modelList.adapter = SelectModelAdapter(modelList)

        // Populate picture list
        val picList = ArrayList<String>()
        for (imageUrls in item.picUrl) {
            picList.add(imageUrls) // Corrected to add URLs to the list
        }

        Glide.with(this)
            .load(picList[0])
            .into(binding.img)

        binding.picList.adapter = PicAdapter(picList) { selectedImageUrl ->
            Glide.with(this)
                .load(selectedImageUrl)
                .into(binding.img)
        }
        binding.picList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "$"+item.price
        binding.ratingTxt.text = "${item.rating} Rating"

        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managmentCart.insertItem(item)
        }

        binding.backBtn.setOnClickListener { finish() }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
    }
}
