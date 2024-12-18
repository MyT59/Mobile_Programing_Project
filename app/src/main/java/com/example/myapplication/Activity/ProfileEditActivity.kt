package com.example.myapplication.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.Helper.DatabaseHelper
import com.example.myapplication.R

class ProfileEditActivity : BaseActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var profileNameEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var captureImageButton: Button
    private lateinit var databaseHelper: DatabaseHelper

    private val CAMERA_REQUEST_CODE = 1001
    private val CAMERA_PERMISSION_CODE = 1002
    private var profileImage: Bitmap? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        profileImageView = findViewById(R.id.profile_image_view)
        profileNameEditText = findViewById(R.id.profile_name_edit_text)
        saveButton = findViewById(R.id.save_button)
        captureImageButton = findViewById(R.id.capture_image_button)

        // Get the username from previous activity
        username = intent.getStringExtra("username")

        databaseHelper = DatabaseHelper(this)

        // Handle camera click
        captureImageButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                // Request Camera permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }

        // Handle save button click
        saveButton.setOnClickListener {
            val profileName = profileNameEditText.text.toString()

            if (profileName.isNotEmpty() && profileImage != null && username != null) {
                val result = databaseHelper.updateUserProfile(username!!, profileName, profileImage!!)
                if (result > 0) {
                    Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Failed to save profile.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter name and upload a picture.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle the camera result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            profileImage = data?.extras?.get("data") as Bitmap
            profileImageView.setImageBitmap(profileImage)
        }
    }

    // Handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission is required to take a picture", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Open the camera
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }
}
