package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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

        // Initialize views
        signupUsername = findViewById(R.id.signup_username)
        signupEmail = findViewById(R.id.signup_email)
        signupPassword = findViewById(R.id.signup_password)
        signupButton = findViewById(R.id.signup_button)
        loginRedirectText = findViewById(R.id.loginRedirectText)

        // Initialize the DatabaseHelper
        databaseHelper = DatabaseHelper(this)  // Pass the context to the database helper

        // Handle sign-up button click
        signupButton.setOnClickListener {
            val username = signupUsername.text.toString().trim()
            val email = signupEmail.text.toString().trim()
            val password = signupPassword.text.toString().trim()

            // Check if input fields are not empty
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Check if the username already exists
                if (!databaseHelper.checkUserExists(username)) {
                    // Insert new user into the database
                    val result = databaseHelper.insertUser(username, email, password)

                    if (result != -1L) {  // If insert was successful
                        Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()

                        // After successful registration, navigate to Profile Setup screen
                        val intent = Intent(this, ProfileEditActivity::class.java)
                        intent.putExtra("username", username)  // Pass username to next activity
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

        // Redirect to login activity
        loginRedirectText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
