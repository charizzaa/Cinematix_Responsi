package com.example.cinematix_responsi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // Start the main activity after the delay
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close the splash screen activity
        }, 3000)
    }
}