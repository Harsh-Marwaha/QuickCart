package com.harsh.quickcart.Activites.Activites

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.Activites.Models.productsModels.GetProductsItem
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemsHomeActivity : AppCompatActivity() {

    private val TAG = ItemsHomeActivity::class.java.simpleName

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
        var btnAddItem : ImageButton = findViewById(R.id.btnAddItem)
        var btnRemoveItem : ImageButton = findViewById(R.id.btnRemoveItem)

        var arrProducts :  GetProducts? = null

        var product : GetProducts? = null

        var db = Firebase.firestore

        Glide.with(applicationContext).load(images).into(ivItemImage)
        tvItemTitle.text = title
        tvItemDescription.text = description
        tvItemPrice.text = "$"+price.toString()

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/categories/$id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(StoreService::class.java)

        val call: Call< GetProducts> = retrofitAPI.getProducts()

        call.enqueue(object : Callback< GetProducts> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call< GetProducts>, response: Response< GetProducts>) {

                if (response!=null){
                    Log.d(TAG, "GetCart : onResponse: $response.body().toString()}")
                }

                if (response.body() != null){
                    arrProducts = response.body()!!
//                    product = response.body()
                }
            }

            override fun onFailure(call: Call< GetProducts>, t: Throwable) {

                Log.d(TAG, "GetCart : onResponse: ${t.message.toString()}")
                Toast.makeText(applicationContext,"Error found is : ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })

        var count : Int = 0

        btnAddItem.setOnClickListener(){
            val userMap = hashMapOf(
                "product" to product,
                "Count" to count,
            )

            if (userId != null) {
                db.collection("Cart").document(userId).set(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Product added to cart successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(applicationContext, "Could not add this product to cart", Toast.LENGTH_SHORT)
                            .show()
                    }
            }

            val ref = userId?.let { it1 -> db.collection("Cart").document(it1) }
            ref?.get()?.addOnSuccessListener {

                if (it != null){

                    val updateUserMap = mapOf(
                        "id" to arrProducts?.get(0)?.id,
                        "price" to arrProducts?.get(0)?.price,
                        "description" to arrProducts?.get(0)?.description,
                        "title" to arrProducts?.get(0)?.title,
                        "images" to arrProducts?.get(0)?.images,
                        "category id" to arrProducts?.get(0)?.category?.id,
                        "category name" to arrProducts?.get(0)?.category?.name,
                        "Count" to count++,
                    )

                    db.collection("Cart").document(userId).update(updateUserMap)
                }

                else{
                    Log.d(TAG, "set on click listener")

                    db.collection("Cart").document(userId).set(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(applicationContext, "Product added to cart successfully", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext, "Could not add this product to cart", Toast.LENGTH_SHORT)
                                .show()
                        }

                }
            }?.addOnFailureListener{
                Toast.makeText(applicationContext, "Failed to add this product to cart", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        btnRemoveItem.setOnClickListener(){

            val ref = userId?.let { it1 -> db.collection("Cart").document(it1) }
            ref?.get()?.addOnSuccessListener {
                if (it != null){

                    var productCount = it.data?.get("Count")

                    if(productCount == 1){
                        db.collection("Cart").document(userId).delete()
                    }

                    else{
                        val updateUserMap = mapOf(
                            "product" to product,
                            "Count" to count--,
                        )

                        db.collection("Cart").document(userId).update(updateUserMap)
                    }

                }

            }?.addOnFailureListener{
                Toast.makeText(applicationContext, "Failed to remove this product from cart", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }
}