package com.harsh.quickcart.Activites.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter.onItemClickListener
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetCategories
import com.harsh.quickcart.R

class CategoriesRecViewAdapter : RecyclerView.Adapter<CategoriesRecViewAdapter.ViewHolder> {

    private var context : Context? = null
    private var arrCategories : GetCategories? = null

    private lateinit var mListener : onItemClickListener

    constructor(context: Context?, arrCategories: GetCategories?){
        this.context=context
        this.arrCategories=arrCategories
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesRecViewAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.row_categories, parent, false),mListener)
    }

    override fun onBindViewHolder(holder: CategoriesRecViewAdapter.ViewHolder, position: Int) {
        holder.tvCategories.text = arrCategories?.get(position)?.name.toString()
        Glide.with(context!!).load(arrCategories?.get(position)?.image)
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