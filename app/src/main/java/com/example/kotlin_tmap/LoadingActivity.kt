package com.example.kotlin_tmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kotlin_tmap.map.MapExample

class   LoadingActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, MapExample::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }

}