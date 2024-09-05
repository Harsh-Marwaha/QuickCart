package com.harsh.quickcart.Activites.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harsh.quickcart.Activites.Models.CategoriesModel
import com.harsh.quickcart.R

class CategoriesRecViewAdapter : RecyclerView.Adapter<CategoriesRecViewAdapter.ViewHolder> {

    private var context : Context? = null
    private var arrCategories : ArrayList<CategoriesModel>? = null

    constructor(context: Context?, arrCategories: ArrayList<CategoriesModel>?){
        this.context=context
        this.arrCategories=arrCategories
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesRecViewAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.row_categories, parent, false))
    }

    override fun onBindViewHolder(holder: CategoriesRecViewAdapter.ViewHolder, position: Int) {
        holder.tvCategories.text = arrCategories?.get(position)?.title.toString()
        Glide.with(context!!).load(arrCategories?.get(position)?.image)
            .into(holder.imgViewCategories)
    }

    override fun getItemCount(): Int {
        return arrCategories!!.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imgViewCategories = itemView.findViewById<ImageView>(R.id.imgViewCategories)
        var tvCategories = itemView.findViewById<TextView>(R.id.tvCategories)
    }
}