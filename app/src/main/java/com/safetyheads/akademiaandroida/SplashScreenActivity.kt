package com.safetyheads.akademiaandroida


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 3000 // 3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}