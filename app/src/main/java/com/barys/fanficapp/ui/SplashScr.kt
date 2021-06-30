package com.barys.fanficapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.barys.fanficapp.R

class SplashScr : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scr)

        supportActionBar?.hide()
        Handler().postDelayed({
            val intent = Intent(this@SplashScr, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
