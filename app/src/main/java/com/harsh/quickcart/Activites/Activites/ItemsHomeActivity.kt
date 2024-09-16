package com.harsh.quickcart.Activites.Activites

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.harsh.quickcart.R

class ItemsHomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_items_home)

        var intent : Intent = getIntent()

        var  description : String = intent.getStringExtra("description").toString()
        var  id : Int = intent.getIntExtra("id",0)
        var  images : String = intent.getStringExtra("images").toString()
        var  price : Int? = intent.getIntExtra("price",0)
        var  title : String = intent.getStringExtra("title").toString()

        var ivItemImage : ImageView = findViewById(R.id.ivItemImage)
        var tvItemTitle : TextView = findViewById(R.id.tvItemTitle)
        var tvItemDescription : TextView = findViewById(R.id.tvItemDescription)
        var tvItemPrice : TextView = findViewById(R.id.tvItemPrice)

        Glide.with(applicationContext).load(images).into(ivItemImage)
        tvItemTitle.text = title
        tvItemDescription.text = description
        tvItemPrice.text = "$"+price.toString()

    }
}