package com.example.myapplication.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Helper.DatabaseHelper
import com.example.myapplication.R

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var loginRedirectText: TextView
    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.signup_username)
        passwordEditText = findViewById(R.id.signup_password)
        signInButton = findViewById(R.id.signin_button)
        loginRedirectText = findViewById(R.id.loginRedirectText)

        databaseHelper = DatabaseHelper(this)

        signInButton.setOnClickListener {
            handleSignIn()
        }

        loginRedirectText.setOnClickListener {
            redirectToSignUp()
        }
    }

    private fun handleSignIn() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isNotBlank() && password.isNotBlank()) {
            if (databaseHelper.validateUser(username, password)) {
                // Navigate to MainActivity and pass the username
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
        }
    }


    private fun redirectToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
