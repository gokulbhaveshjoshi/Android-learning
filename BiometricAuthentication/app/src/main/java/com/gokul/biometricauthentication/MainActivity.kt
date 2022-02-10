package com.gokul.biometricauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var biometricPrompt: BiometricPrompt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Authentication Error: $errorCode, $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Authenication Success" , Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authenication Failed", Toast.LENGTH_SHORT).show()
                }
            }
        )
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometeric login for your app")
            .setSubtitle("Login in using your biometeric credentail")
            .setNegativeButtonText("Use account password")
            .build()

        val biometericLogin = findViewById<Button>( R.id.btnLogin)
        biometericLogin.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }
    }
}