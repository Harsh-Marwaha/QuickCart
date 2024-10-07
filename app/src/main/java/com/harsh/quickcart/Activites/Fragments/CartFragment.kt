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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.harsh.quickcart.Activites.Adapters.CartRecViewAdapter
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.CartModels.CartModel
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.Activites.Models.productsModels.GetSingleProduct
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
    var db = Firebase.firestore
    var arrCart : ArrayList<CartModel>? = ArrayList()

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
        loadingPB?.visibility=View.VISIBLE
        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        getProducts()
        val ref = db.collection("Cart").get()
            ?.addOnSuccessListener {
                loadingPB?.visibility=View.GONE
                var arr = it.documents
                for (i in arr.indices){

                    Log.d(TAG, "arr: ${arr.get(i).data?.get("product")}")
                    var gson = Gson()
                    var arrProduct = gson?.fromJson(arr.get(i).data?.get("product").toString(), GetSingleProduct::class.java)
                    var model = CartModel(uid = arr.get(i).data?.get("uid")?.toString(), product = arrProduct, productId = arr.get(i).data?.get(" product id"), count = arr.get(i).data?.get("count"))
                    arrCart?.add(model)
                    cartRecViewAdapter = activity?.let { CartRecViewAdapter(it, arrCart) }
                    recViewCart?.adapter = cartRecViewAdapter
                    recViewCart?.layoutManager = LinearLayoutManager(context)
                }
            }
            ?.addOnFailureListener(){

            }
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
            override fun onResponse(call: Call<GetProducts>, response: Response<GetProducts>) {

                if (response!=null){
                    Log.d(TAG, "GetCartProducts : onResponse: ${response.body().toString()}")
                }

                loadingPB?.visibility = View.GONE

                if (response.body() != null){
                    arrProducts = response.body()
                    cartRecViewAdapter = activity?.let { CartRecViewAdapter(it, arrCart) }
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