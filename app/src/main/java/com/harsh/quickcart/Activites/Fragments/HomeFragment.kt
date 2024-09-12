package com.harsh.quickcart.Activites.Fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.java.simpleName

    var recViewHome : RecyclerView? = null
    var homeRecViewAdapter : HomeRecViewAdapter? = null
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recViewHome = view.findViewById(R.id.recViewHome)
        loadingPB = view.findViewById(R.id.idLoadingPB)
        searchView = view.findViewById(R.id.searchView)

        getProducts()

    }

    private fun getProducts() {

        loadingPB?.visibility = View.VISIBLE


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(StoreService::class.java)

        val call: Call<GetProducts> = retrofitAPI.getProducts()

        call.enqueue(object : Callback<GetProducts> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<GetProducts>, response: Response<GetProducts>) {

//                var arrProducts : ArrayList<GetProducts>
//                arrProducts= arrayListOf(response.body()!!)
                var productsTitle : List<String>
                var productsPrice : List<Double>

//                arrProducts = listOf(response.body()?.image!!)
//                productsTitle = listOf(response.body()?.title!!)
//                productsPrice = listOf(response.body()?.price!!)


                if (response!=null){
                    Log.d(TAG, "GetProducts : onResponse: $response.body().toString()}")
                }

                loadingPB?.visibility = View.GONE

                if (response.body() != null){
                    arrProducts = response.body()
                    homeRecViewAdapter = activity?.let { HomeRecViewAdapter(it, arrProducts) }
                    recViewHome?.adapter = homeRecViewAdapter
                    recViewHome?.layoutManager = GridLayoutManager(context,2)
                   homeRecViewAdapter?.notifyDataSetChanged();
                }
            }

            override fun onFailure(call: Call<GetProducts>, t: Throwable) {

                Log.d(TAG, "GetProducts : onResponse: ${t.message.toString()}")
                Toast.makeText(context,"Error found is : ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}