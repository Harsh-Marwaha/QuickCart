package com.harsh.quickcart.Activites.Fragments

import android.annotation.SuppressLint
import android.content.Intent
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
import com.harsh.quickcart.Activites.Activites.ItemsCategoriesActivity
import com.harsh.quickcart.Activites.Activites.ItemsHomeActivity
import com.harsh.quickcart.Activites.Adapters.CategoriesRecViewAdapter
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.CategoriesModels.CategoriesFragmentModel
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetCategories
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetSingleCategory
//import com.harsh.quickcart.Activites.Models.productsModels.GetCategories
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CategoriesFragment : Fragment() {

    private val TAG = CategoriesFragment::class.java.simpleName

    var recViewCategories : RecyclerView? = null
    var categoriesRecViewAdapter : CategoriesRecViewAdapter? = null
    var loadingPB: ProgressBar? = null
    var searchView : SearchView? = null
    private var arrProducts : GetCategories? = null
    private var arrGetSingleCategory : GetSingleCategory? = null
    private var arrImage : String? = null
    private var arrname : String? = null
    var categoriesFragmentModel : CategoriesFragmentModel? = null
    var arrcategoriesFragmentModel : ArrayList<CategoriesFragmentModel>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recViewCategories = view.findViewById(R.id.recViewCategories)
        loadingPB = view.findViewById(R.id.idLoadingPB)
        searchView = view.findViewById(R.id.searchView)

        GetCategories()

    }

    private fun GetCategories() {

        loadingPB?.visibility = View.VISIBLE

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(StoreService::class.java)

        val call: Call<GetCategories> = retrofitAPI.getCategories()

        call.enqueue(object : Callback<GetCategories> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<GetCategories>, response: Response<GetCategories>) {

                if (response!=null){
                    Log.d(TAG, "GetCategories : onResponse: ${response.body().toString()}")
                }



                if (response.body() != null){
                    arrProducts = response.body()

                    val retrofitImage = Retrofit.Builder()
                        .baseUrl("https://fakestoreapi.com/products/category/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val retrofitAPIImage = retrofitImage.create(StoreService::class.java)

                    for (i in 0 until arrProducts!!.size){
                        val callImage: Call<GetSingleCategory> = retrofitAPIImage.getSingleCategory(arrProducts?.get(i).toString())

                        callImage.enqueue(object : Callback<GetSingleCategory> {
                            @SuppressLint("NotifyDataSetChanged")
                            override fun onResponse(call: Call<GetSingleCategory>, response: Response<GetSingleCategory>) {

                                if (response!=null){
                                    Log.d(TAG, "GetCategoryImage : onResponse: ${response.body().toString()}")
                                }

                                if (response.body() != null){
                                    arrGetSingleCategory = response.body()
                                    Log.d(TAG, "GetCategoryImageee : onResponse: ${arrGetSingleCategory?.get(0)?.image}")
                                }
                            }

                            override fun onFailure(call: Call<GetSingleCategory>, t: Throwable) {

                                Log.d(TAG, "GetCategoryImage : onResponse: ${t.message.toString()}")
                                Toast.makeText(context,"Could Not Load Image : ${t.message}", Toast.LENGTH_SHORT).show()

                            }
                        })
                    }

//                    if (arrGetSingleCategory!=null){
//                        for(i in 0 until arrGetSingleCategory!!.size){
//                            arrname=arrGetSingleCategory?.get(i)?.category
//                            arrImage=arrGetSingleCategory?.get(i)?.image
//                            categoriesFragmentModel=CategoriesFragmentModel(arrname,arrImage)
//                            arrcategoriesFragmentModel?.add(categoriesFragmentModel!!)
//                        }
//                    }
                    categoriesFragmentModel = CategoriesFragmentModel(arrProducts,arrGetSingleCategory)
                    Log.d(TAG, "parse : onResponse: ${categoriesFragmentModel!!.image?.get(0)}")
                    categoriesRecViewAdapter = CategoriesRecViewAdapter(context,arrProducts,arrGetSingleCategory)
                    recViewCategories?.adapter = categoriesRecViewAdapter
                    recViewCategories?.layoutManager = GridLayoutManager(context,2)
                    categoriesRecViewAdapter?.notifyDataSetChanged();

                    categoriesRecViewAdapter?.onItemClickListener(object : CategoriesRecViewAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            loadingPB?.visibility = View.VISIBLE

                            var intent = Intent(context, ItemsCategoriesActivity::class.java)
                            intent.putExtra("id",arrProducts?.get(position))

                            loadingPB?.visibility = View.GONE

                            startActivity(intent)
                        }
                    })
                }
                loadingPB?.visibility = View.GONE
            }

            override fun onFailure(call: Call<GetCategories>, t: Throwable) {
                loadingPB?.visibility = View.GONE
                Log.d(TAG, "GetCategories : onResponse: ${t.message.toString()}")
                Toast.makeText(context,"Error found is : ${t.message}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}