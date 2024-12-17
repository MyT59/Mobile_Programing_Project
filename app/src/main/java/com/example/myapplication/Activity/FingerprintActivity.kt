package com.example.myapplication.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.myapplication.R


class FingerprintActivity : AppCompatActivity() {

    private lateinit var enableFingerprintButton: Button
    private lateinit var disableFingerprintButton: Button
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fingerprint)

        enableFingerprintButton = findViewById(R.id.enable_fingerprint_button)
        disableFingerprintButton = findViewById(R.id.disable_fingerprint_button)

        biometricManager = BiometricManager.from(this)

        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = androidx.biometric.BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Fingerprint Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    enableFingerprintPreference(true)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        enableFingerprintButton.setOnClickListener {
            showBiometricPrompt()
        }

        disableFingerprintButton.setOnClickListener {
            enableFingerprintPreference(false)
            Toast.makeText(this, "Fingerprint Disabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showBiometricPrompt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Add Fingerprint")
            .setSubtitle("Touch the fingerprint sensor to add your fingerprint")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun enableFingerprintPreference(isEnabled: Boolean) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("fingerprint_enabled", isEnabled).apply()

        enableFingerprintButton.isEnabled = !isEnabled
        disableFingerprintButton.isEnabled = isEnabled
    }
}
