package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Helper.DatabaseHelper
import com.example.myapplication.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var profileNameTextView: TextView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var fingerprintButton: ImageView
    private lateinit var cartBtn: ImageView // Assuming you have a cart button in your layout
    private lateinit var homeIcon: ImageView // Assuming you have a home icon button in your layout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImageView = findViewById(R.id.profile_image)
        profileNameTextView = findViewById(R.id.profile_name)
        databaseHelper = DatabaseHelper(this)
        fingerprintButton = findViewById(R.id.fingerprintButton)
        cartBtn = findViewById(R.id.cartBtn) // Bind to your cart button
        homeIcon = findViewById(R.id.homeIcon) // Bind to your home icon

        val username = intent.getStringExtra("username")

        if (username != null) {
            val profileData = databaseHelper.getUserProfile(username)
            if (profileData != null) {
                profileNameTextView.text = profileData.first

                val profilePictureBytes = profileData.second
                if (profilePictureBytes != null) {
                    val bitmap = BitmapFactory.decodeByteArray(profilePictureBytes, 0, profilePictureBytes.size)
                    profileImageView.setImageBitmap(bitmap)
                }
            } else {
                Toast.makeText(this, "Profile data not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No username provided", Toast.LENGTH_SHORT).show()
        }

        fingerprintButton.setOnClickListener {
            redirectToFingerPrint()
        }

        // Cart Button Listener - Navigates to CartActivity
        cartBtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Home Icon Listener - Navigates to MainActivity
        homeIcon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun redirectToFingerPrint() {
        val intent = Intent(this, FingerprintActivity::class.java)
        startActivity(intent)
    }
}
