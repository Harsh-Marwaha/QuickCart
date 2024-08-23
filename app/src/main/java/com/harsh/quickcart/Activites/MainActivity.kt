package com.harsh.quickcart.Activites

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.harsh.quickcart.R

class MainActivity : AppCompatActivity() {

    var edtMobileNumber : EditText? = null
    var btnContinue : Button? = null
    var btnGoogle : Button? = null
    var btnFacebook : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtMobileNumber = findViewById(R.id.edtMobileNumber)
        btnContinue = findViewById(R.id.btnContinue)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)

    }
}