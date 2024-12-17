package com.example.myapplication.Activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.CartAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCartBinding
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root) // Fixed to use the binding root

        managementCart = ManagmentCart(this)

        setVariable()
        initCartList()
        calculateCart() // Corrected the spelling
    }

    private fun initCartList() {
        binding.viewCart.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewCart.adapter =
            CartAdapter(managementCart.getListCart(), this, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart() // Ensure the method name matches
                }
            })

        with(binding) {
            empytTxt.visibility =
                if (managementCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView4.visibility =
                if (managementCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun setVariable() {
        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }

            method1.setOnClickListener {
                method1.setBackgroundResource(R.drawable.lavender_bttn_background)
                methodIc1.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@CartActivity,
                        R.color.purple
                    )
                )
                methodTitle1.setTextColor(ContextCompat.getColor(this@CartActivity, R.color.purple))
                methodSubtitle1.setTextColor(
                    ContextCompat.getColor(
                        this@CartActivity,
                        R.color.purple
                    )
                )

                method2.setBackgroundResource(R.drawable.grey_bg)
                methodIc2.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity, R.color.black))
                methodTitle2.setTextColor(ContextCompat.getColor(this@CartActivity, R.color.black))
                methodSubtitle2.setTextColor(
                    ContextCompat.getColor(
                        this@CartActivity,
                        R.color.grey
                    )
                )
            }

            method2.setOnClickListener {
                method2.setBackgroundResource(R.drawable.lavender_bttn_background)
                methodIc2.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@CartActivity,
                        R.color.purple
                    )
                )
                methodTitle2.setTextColor(ContextCompat.getColor(this@CartActivity, R.color.purple))
                methodSubtitle2.setTextColor(
                    ContextCompat.getColor(
                        this@CartActivity,
                        R.color.purple
                    )
                )

                method1.setBackgroundResource(R.drawable.grey_bg)
                methodIc1.imageTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity, R.color.black))
                methodTitle1.setTextColor(ContextCompat.getColor(this@CartActivity, R.color.black))
                methodSubtitle1.setTextColor(
                    ContextCompat.getColor(
                        this@CartActivity,
                        R.color.grey
                    )
                )
            }
        }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0
        val tax = Math.round(managementCart.getTotalFee() * percentTax * 100) / 100.0
        val total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100.0
        val itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100.0

        with(binding) {
            totalFeeTxt.text = "$$itemTotal"
            taxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }
}