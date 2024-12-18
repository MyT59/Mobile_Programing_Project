package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityIntro2Binding

class IntroActivity2 : BaseActivity() {
    private lateinit var binding: ActivityIntro2Binding
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize BiometricPrompt
        initializeBiometricPrompt()

        binding.apply {
            startedbtn.setOnClickListener {
                // Check if the user wants to authenticate with the fingerprint or just proceed
                showFingerprintPrompt()
            }
            textView5.setOnClickListener {
                startActivity(Intent(this@IntroActivity2, SignUpActivity::class.java))
            }
            textView4.setOnClickListener {
                startActivity(Intent(this@IntroActivity2, LoginActivity::class.java))
            }
        }
    }

    private fun initializeBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // Proceed to MainActivity upon successful authentication
                    startActivity(Intent(this@IntroActivity2, MainActivity::class.java))
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Handle authentication error (e.g., show a toast or dialog)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Handle failed authentication (e.g., show a toast)
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock App")
            .setSubtitle("Authenticate with your fingerprint to proceed")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()
    }

    private fun showFingerprintPrompt() {
        val biometricManager = BiometricManager.from(this)

        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
            // If biometric is available, show the fingerprint prompt
            biometricPrompt.authenticate(promptInfo)
        } else {
            // If biometric is not available, just proceed to MainActivity
            startActivity(Intent(this@IntroActivity2, MainActivity::class.java))
        }
    }
}
