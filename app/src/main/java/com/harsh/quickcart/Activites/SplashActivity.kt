package com.harsh.quickcart.Activites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.harsh.quickcart.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val intent = Intent(this,MainActivity::class.java)

        Handler().postDelayed(
            Runnable { kotlin.run{
            startActivity(intent)
            finish()}
                     },2500)



    }
}