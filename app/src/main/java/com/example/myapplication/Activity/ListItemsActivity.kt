package com.example.myapplication.Activity

import android.os.Bundle
import android.view.View

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Adapter.ListItemsAdapter
import com.example.myapplication.databinding.ActivityListItemsBinding
import com.example.myapplication.viewModel.MainViewModel

class ListItemsActivity : BaseActivity() {
    private lateinit var binding: ActivityListItemsBinding
    private val viewModel = MainViewModel()
    private var id: String = ""
    private var title: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        initList()

    }

    private fun initList() {
       binding.apply{
           progressBarList.visibility= View.VISIBLE
           viewModel.recommended.observe(this@ListItemsActivity, Observer {
               viewList.layoutManager= GridLayoutManager(this@ListItemsActivity,2)
               viewList.adapter= ListItemsAdapter(it)
               progressBarList.visibility= View.GONE
           })
           viewModel.loadFiltered(id)
       }
    }

    private fun getBundle() {
        id = intent.getStringExtra("id")!!
        title = intent.getStringExtra("title")!!

        binding.categoryTxt.text = title
    }
}
