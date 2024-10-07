package com.harsh.quickcart.Activites.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetCategories
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetSingleCategory
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.R

class ItemsCategoryRecViewAdapter : RecyclerView.Adapter<ItemsCategoryRecViewAdapter.ViewHolder> {
    private var context : Context? = null
    private var arrProducts : GetSingleCategory? = null

    private lateinit var mListener : onItemClickListener

    constructor(context: Context, arrProducts: GetSingleCategory?){
        this.context=context
        this.arrProducts=arrProducts
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemsCategoryRecViewAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.row_home,parent,false),mListener)
    }

    class ViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        var productsImage = itemView.findViewById<ImageView>(R.id.productsImage)
        var productsTitle = itemView.findViewById<TextView>(R.id.productsTitle)
        var productsPrice = itemView.findViewById<TextView>(R.id.productsPrice)

    }

    override fun onBindViewHolder(holder: ItemsCategoryRecViewAdapter.ViewHolder, position: Int) {
        holder.productsTitle.text = arrProducts?.get(position)?.title
        holder.productsPrice.text = "$"+arrProducts?.get(position)?.price
        Glide.with(context!!).load(arrProducts!![position].image)
            .into(holder.productsImage)
    }

    override fun getItemCount(): Int {
        return arrProducts?.size ?: 0
    }

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun onItemClickListener(listener: onItemClickListener)
    {
        mListener = listener
    }
}