package com.example.myapplication

import android.content.Intent
import android.graphics.Matrix
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityIntro2Binding

class IntroActivity2 : BaseActivity() {
    private lateinit var binding: ActivityIntro2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            startedbtn.setOnClickListener {
                startActivity(Intent(this@IntroActivity2, MainActivity::class.java))
            }
            textView5.setOnClickListener {
                startActivity(Intent(this@IntroActivity2, SignUpActivity::class.java))
            }
        }
    }
}
