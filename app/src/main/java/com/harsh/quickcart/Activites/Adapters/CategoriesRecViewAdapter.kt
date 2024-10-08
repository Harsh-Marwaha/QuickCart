package com.harsh.quickcart.Activites.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harsh.quickcart.Activites.Activites.ItemsHomeActivity
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter.onItemClickListener
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Fragments.CategoriesFragment
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetCategories
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetSingleCategory
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoriesRecViewAdapter : RecyclerView.Adapter<CategoriesRecViewAdapter.ViewHolder> {

    private val TAG = CategoriesRecViewAdapter::class.java.simpleName

    private var context : Context? = null
    private var arrCategories : GetCategories? = null
    private var arrProducts : GetSingleCategory? = null

    private lateinit var mListener : onItemClickListener

    constructor(context: Context?, arrCategories: GetCategories?, arrProducts : GetSingleCategory? = null){
        this.context=context
        this.arrCategories=arrCategories
        this.arrProducts=arrProducts
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesRecViewAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.row_categories, parent, false),mListener)
    }

    override fun onBindViewHolder(holder: CategoriesRecViewAdapter.ViewHolder, position: Int) {
        holder.tvCategories.text = arrCategories?.get(position).toString()
        Log.d(TAG, "GetCategoryImages : onResponse: ${arrProducts}")
        Glide.with(context!!).load(arrProducts?.get(0)?.image)
            .into(holder.imgViewCategories)
    }

    override fun getItemCount(): Int {
        return arrCategories!!.size
    }

    class ViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        var imgViewCategories = itemView.findViewById<ImageView>(R.id.imgViewCategories)
        var tvCategories = itemView.findViewById<TextView>(R.id.tvCategories)
    }

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun onItemClickListener(listener: onItemClickListener)
    {
        mListener = listener
    }

}