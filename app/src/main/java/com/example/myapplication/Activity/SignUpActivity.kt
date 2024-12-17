package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Helper.DatabaseHelper
import com.example.myapplication.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var signupUsername: EditText
    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupButton: Button
    private lateinit var loginRedirectText: TextView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupUsername = findViewById(R.id.signup_username)
        signupEmail = findViewById(R.id.signup_email)
        signupPassword = findViewById(R.id.signup_password)
        signupButton = findViewById(R.id.signup_button)
        loginRedirectText = findViewById(R.id.loginRedirectText)

        databaseHelper = DatabaseHelper(this)

        signupButton.setOnClickListener {
            val username = signupUsername.text.toString().trim()
            val email = signupEmail.text.toString().trim()
            val password = signupPassword.text.toString().trim()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (!databaseHelper.checkUserExists(username)) {
                    val result = databaseHelper.insertUser(username, email, password)

                    if (result != -1L) {
                        Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, ProfileEditActivity::class.java)
                        intent.putExtra("username", username)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show()
            }
        }

        loginRedirectText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
