package com.example.budgetmanager

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<ProgressBar>(R.id.progressBar).progress=100
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<ProgressBar>(R.id.progressBar).progress=50
        },1500)
        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<ProgressBar>(R.id.progressBar).progress=60
        },2000)
        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<ProgressBar>(R.id.progressBar).progress=70
        },2400)
    }
}