package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()


        Handler().postDelayed(Runnable {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }, 2000
            )

    }
}