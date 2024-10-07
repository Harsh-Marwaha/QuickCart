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
import com.harsh.quickcart.Activites.Adapters.ItemsCategoryRecViewAdapter
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Fragments.HomeFragment
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetCategories
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetSingleCategory
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
    var itemsCategoryRecViewAdapter : ItemsCategoryRecViewAdapter? = null
    var loadingPB: ProgressBar? = null
    var searchView : SearchView? = null
    private var arrProducts : GetSingleCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_items_categories2)

        recViewCategoriesItems = findViewById(R.id.recViewCategoriesItems)
        loadingPB = findViewById(R.id.idLoadingPB)
        searchView = findViewById(R.id.searchView)

        var  id : String = intent.getStringExtra("id").toString()
        getProducts(id)
    }

    private fun getProducts(id : String) {

        loadingPB?.visibility = View.VISIBLE


        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/products/category/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(StoreService::class.java)

        val call: Call<GetSingleCategory> = retrofitAPI.getSingleCategory(id)

        call.enqueue(object : Callback<GetSingleCategory> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<GetSingleCategory>, response: Response<GetSingleCategory>) {

                if (response!=null){
                    Log.d(TAG, "GetCategory : onResponse: $response.body().toString()}")
                }

                loadingPB?.visibility = View.GONE

                if (response.body() != null){
                    arrProducts = response.body()
                    itemsCategoryRecViewAdapter =  ItemsCategoryRecViewAdapter(applicationContext,arrProducts)
                    recViewCategoriesItems?.adapter = itemsCategoryRecViewAdapter
                    recViewCategoriesItems?.layoutManager = GridLayoutManager(applicationContext,2)
                    itemsCategoryRecViewAdapter?.notifyDataSetChanged();

                    itemsCategoryRecViewAdapter?.onItemClickListener(object : ItemsCategoryRecViewAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            var intent = Intent(applicationContext, ItemsHomeActivity::class.java)
                            intent.putExtra("description",arrProducts?.get(position)?.description)
                            intent.putExtra("id",arrProducts?.get(position)?.id)
                            intent.putExtra("images",arrProducts?.get(position)?.image)
                            intent.putExtra("price",arrProducts?.get(position)?.price)
                            intent.putExtra("title",arrProducts?.get(position)?.title)
                            startActivity(intent)
                        }
                    })

                }
            }

            override fun onFailure(call: Call<GetSingleCategory>, t: Throwable) {

                Log.d(TAG, "GetCategory : onResponse: ${t.message.toString()}")
                Toast.makeText(applicationContext,"Error found is : ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}