package com.harsh.quickcart.Activites.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harsh.quickcart.Activites.Adapters.CartRecViewAdapter
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CartFragment : Fragment() {

    private val TAG = CartFragment::class.java.simpleName

    var recViewCart : RecyclerView? = null
    var cartRecViewAdapter : CartRecViewAdapter? = null
    var loadingPB: ProgressBar? = null
    var searchView : SearchView? = null
    private var arrProducts : GetProducts? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recViewCart = view.findViewById(R.id.recViewCart)
        loadingPB = view.findViewById(R.id.idLoadingPB)
        searchView = view.findViewById(R.id.searchView)
        getProducts()
    }

    private fun getProducts() {

        loadingPB?.visibility = View.VISIBLE


        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(StoreService::class.java)

        val call: Call<GetProducts> = retrofitAPI.getProducts()

        call.enqueue(object : Callback<GetProducts> {
            override fun onResponse(call: Call<GetProducts>, response: Response<GetProducts>) {

                if (response!=null){
                    Log.d(TAG, "GetCartProducts : onResponse: $response.body().toString()}")
                }

                loadingPB?.visibility = View.GONE

                if (response.body() != null){
                    arrProducts = response.body()
                    cartRecViewAdapter = activity?.let { CartRecViewAdapter(it, arrProducts) }
                    recViewCart?.adapter = cartRecViewAdapter
                    recViewCart?.layoutManager = LinearLayoutManager(context)
                }
            }

            override fun onFailure(call: Call<GetProducts>, t: Throwable) {

                Log.d(TAG, "GetCartProducts : onResponse: ${t.message.toString()}")
                Toast.makeText(context,"Error found is : ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }

}