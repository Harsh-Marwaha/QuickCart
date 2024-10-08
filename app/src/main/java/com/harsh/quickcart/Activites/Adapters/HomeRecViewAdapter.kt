package com.harsh.quickcart.Activites.Adapters

import android.content.Context
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.R

class HomeRecViewAdapter : RecyclerView.Adapter<HomeRecViewAdapter.ViewHolder> {

    private var context : Context? = null
    private var arrProducts : GetProducts? = null

    private lateinit var mListener : onItemClickListener

    constructor(context: Context, arrProducts:GetProducts?){
        this.context=context
        this.arrProducts=arrProducts
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeRecViewAdapter.ViewHolder {
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

    override fun onBindViewHolder(holder: HomeRecViewAdapter.ViewHolder, position: Int) {
        holder.productsTitle.text = arrProducts?.get(position)?.title
        holder.productsPrice.text = "$"+arrProducts?.get(position)?.price.toString()!!
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