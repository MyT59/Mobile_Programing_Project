package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
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
            textView4.setOnClickListener {
                startActivity(Intent(this@IntroActivity2, LoginActivity::class.java ))
            }
        }
    }
}
