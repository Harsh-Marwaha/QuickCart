package com.harsh.quickcart.Activites.Activites

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.productsModels.GetSingleProduct
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

        ///Show Alert
        val dialog = ProgressDialog.show(
            this@ItemsHomeActivity, "",
            "Loading. Please wait...", true
        )


        var intent : Intent = getIntent()

        var  description : String = intent.getStringExtra("description").toString()
        var  id : Int = intent.getIntExtra("id",0)
        var  images : String = intent.getStringExtra("images").toString()
        var  price : Double? = intent.getDoubleExtra("price", 0.0)
        var  title : String = intent.getStringExtra("title").toString()
        var  ratingRate : String = intent.getStringExtra("ratingRate").toString()
        var  ratingCount : String = intent.getStringExtra("ratingCount").toString()

        var ivItemImage : ImageView = findViewById(R.id.ivItemImage)
        var tvItemTitle : TextView = findViewById(R.id.tvItemTitle)
        var tvItemDescription : TextView = findViewById(R.id.tvItemDescription)
        var tvItemPrice : TextView = findViewById(R.id.tvItemPrice)
        var itemCount : TextView = findViewById(R.id.itemCount)
        var btnAddItem : ImageButton = findViewById(R.id.btnAddItem)
        var btnRemoveItem : ImageButton = findViewById(R.id.btnRemoveItem)

        var product :  GetSingleProduct? = null

        var count : Int = 1

        var db = Firebase.firestore

        Glide.with(applicationContext).load(images).into(ivItemImage)
        tvItemTitle.text = title
        tvItemDescription.text = description
        tvItemPrice.text = "$"+price.toString()
//        itemCount.text =
//            db.collection("Cart").document(id.toString()).get().addOnSuccessListener { if (it.) }.toString()
var productCount = "";
        var ItemCount = id?.let { it1 -> db.collection("Cart").document(it1.toString()) }
        ItemCount?.get()?.addOnSuccessListener {
            if (it != null){

                 productCount = (it.data?.get("count") ?:"").toString()

                if(productCount == null){
                    itemCount.text = "0"
                }

                else{
                    itemCount.text = productCount.toString()
                }

            }

        }?.addOnFailureListener{
            Toast.makeText(applicationContext, "Failed to remove this product from cart", Toast.LENGTH_SHORT)
                .show()
        }


        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(StoreService::class.java)

        val call: Call<GetSingleProduct> = retrofitAPI.getProduct(id)

        call.enqueue(object : Callback<GetSingleProduct> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(p0: Call<GetSingleProduct>, p1: Response<GetSingleProduct>) {
                //Dismiss
                dialog.dismiss();
                if (p1!=null){
                    Log.d(TAG, "GetCart : onResponse: ${p1.body().toString()}")
                }

                if (p1.body() != null){
                    product = p1.body()
                    Log.d(TAG, "onResponse: DataFound>>>>> ${product?.title}")
                }
            }

            override fun onFailure(p0: Call<GetSingleProduct>, p1: Throwable) {
                //Dismiss
                dialog.dismiss();
                Log.d(TAG, "GetCart : onResponse: ${p1.message.toString()}")
                Toast.makeText(applicationContext,"Error found is : ${p1.message}", Toast.LENGTH_SHORT).show()
            }

        })

        btnAddItem.setOnClickListener(){
            Toast.makeText(applicationContext,"added", Toast.LENGTH_SHORT).show()
            if(product != null) {
                val userMap = mapOf(
                    "uid" to userId,
                    "product id" to id,
                    "count" to count,
                    "product" to Gson().toJson(product).toString()
                )


                if (id != null) {
                    db.collection("Cart").document(id.toString()).set(userMap)
                        .addOnSuccessListener {
                            var ItemCount = id?.let { it1 -> db.collection("Cart").document(it1.toString()) }
                            ItemCount?.get()?.addOnSuccessListener {
                                if (it != null){

                                    var productCount = it.data?.get("count")

                                    if(productCount == null){
                                        itemCount.text = "0"
                                    }

                                    else{
                                        itemCount.text = productCount.toString()
                                    }

                                }

                            }
                            Toast.makeText(
                                applicationContext,
                                "Product added to cart successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                applicationContext,
                                "Could not add this product to cart",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                }

                val ref = id?.let { it1 -> db.collection("Cart").document(it1.toString()) }
                ref?.get()?.addOnSuccessListener {

                    if (it != null) {

                        val updateUserMap = mapOf(
                            "count" to count++,
                        )

                        db.collection("Cart").document(id.toString()).update(updateUserMap)
                    } else {
                        Log.d(TAG, "set on click listener")

                        db.collection("Cart").document(id.toString()).set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Product added to cart successfully",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Could not add this product to cart",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                    }
                }?.addOnFailureListener {
                    Toast.makeText(applicationContext, "Failed to add this product to cart", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        btnRemoveItem.setOnClickListener(){
            Toast.makeText(applicationContext,"removed", Toast.LENGTH_SHORT).show()
            val ref = id?.let { it1 -> db.collection("Cart").document(it1.toString()) }

            if(productCount == "1") {
                db.collection("Cart").document(id.toString()).delete()
            }else{
                val updateUserMap = mapOf(
                    "count" to count--
                )

                db.collection("Cart").document(id.toString()).update(updateUserMap)

                var ItemCount = id?.let { it1 -> db.collection("Cart").document(it1.toString()) }
                ItemCount?.get()?.addOnSuccessListener {
                    if (it != null){

                        var productCount = it.data?.get("count")

                        if(productCount == null){
                            itemCount.text = "0"
                        }

                        else{
                            itemCount.text = productCount.toString()
                        }

                    }

                }
            }

           /* ref?.get()?.addOnSuccessListener {
                if (it != null){

                    var productCount = it.data?.get("count")

                    if(productCount == 1){

                      *//*  db.collection("Cart").document(id.toString())
                        //var ItemCount = id?.let { it1 -> db.collection("Cart").document(id.toString()) }
                        ItemCount?.get()?.addOnSuccessListener {
                            if (it != null){

                                var productCount = it.data?.get("count")

                                if(productCount == null){
                                    itemCount.text = "0"
                                }

                                else{
                                    itemCount.text = productCount.toString()
                                }

                            }

                        }*//*
                        itemCount.text = "0"
                        Toast.makeText(applicationContext,"deleted", Toast.LENGTH_SHORT).show()
                        db.collection("Cart").document(id.toString()).delete()
                    }

                    else{
                        val updateUserMap = mapOf(
                            "count" to count--
                        )

                        db.collection("Cart").document(id.toString()).update(updateUserMap)

                        var ItemCount = id?.let { it1 -> db.collection("Cart").document(it1.toString()) }
                        ItemCount?.get()?.addOnSuccessListener {
                            if (it != null){

                                var productCount = it.data?.get("count")

                                if(productCount == null){
                                    itemCount.text = "0"
                                }

                                else{
                                    itemCount.text = productCount.toString()
                                }

                            }

                        }
                    }

                }

            }?.addOnFailureListener{
                Toast.makeText(applicationContext, "Failed to remove this product from cart", Toast.LENGTH_SHORT)
                    .show()
            }*/
        }
    }
}