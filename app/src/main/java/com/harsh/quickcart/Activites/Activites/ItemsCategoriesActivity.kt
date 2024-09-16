package com.harsh.quickcart.Activites.Activites

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Fragments.HomeFragment
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemsCategoriesActivity : AppCompatActivity() {

    private val TAG = ItemsCategoriesActivity::class.java.simpleName

    var recViewCategoriesItems : RecyclerView? = null
    var homeRecViewAdapter : HomeRecViewAdapter? = null
    var loadingPB: ProgressBar? = null
    var searchView : SearchView? = null
    private var arrProducts : GetProducts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_items_categories2)

        recViewCategoriesItems = findViewById(R.id.recViewCategoriesItems)
        loadingPB = findViewById(R.id.idLoadingPB)
        searchView = findViewById(R.id.searchView)

        var  id : Int = intent.getIntExtra("id",0)
        getProducts(id)
    }

    private fun getProducts(id : Int) {

        loadingPB?.visibility = View.VISIBLE


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/categories/$id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(StoreService::class.java)

        val call: Call<GetProducts> = retrofitAPI.getProducts()

        call.enqueue(object : Callback<GetProducts> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<GetProducts>, response: Response<GetProducts>) {

                if (response!=null){
                    Log.d(TAG, "GetProducts : onResponse: $response.body().toString()}")
                }

                loadingPB?.visibility = View.GONE

                if (response.body() != null){
                    arrProducts = response.body()
                    homeRecViewAdapter =  HomeRecViewAdapter(applicationContext,arrProducts)
                    recViewCategoriesItems?.adapter = homeRecViewAdapter
                    recViewCategoriesItems?.layoutManager = GridLayoutManager(applicationContext,2)
                    homeRecViewAdapter?.notifyDataSetChanged();

                    homeRecViewAdapter?.onItemClickListener(object : HomeRecViewAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            var intent = Intent(applicationContext, ItemsHomeActivity::class.java)
                            intent.putExtra("description",arrProducts?.get(position)?.description)
                            intent.putExtra("id",arrProducts?.get(position)?.id)
                            intent.putExtra("images",arrProducts?.get(position)?.images?.get(0))
                            intent.putExtra("price",arrProducts?.get(position)?.price)
                            intent.putExtra("title",arrProducts?.get(position)?.title)
                            startActivity(intent)
                        }
                    })

                }
            }

            override fun onFailure(call: Call<GetProducts>, t: Throwable) {

                Log.d(TAG, "GetProducts : onResponse: ${t.message.toString()}")
                Toast.makeText(applicationContext,"Error found is : ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}