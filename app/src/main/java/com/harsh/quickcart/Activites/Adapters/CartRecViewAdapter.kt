package com.harsh.quickcart.Activites.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter.ViewHolder
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter.onItemClickListener
import com.harsh.quickcart.Activites.Models.CartModels.CartModel
import com.harsh.quickcart.R

class CartRecViewAdapter : RecyclerView.Adapter<CartRecViewAdapter.ViewHolder> {
    private var context : Context? = null

    private var arrProducts : ArrayList<CartModel>? = null

    private lateinit var mListener : onItemClickListener

    constructor(context: Context, arrProducts: ArrayList<CartModel>?){
        this.context=context
        this.arrProducts=arrProducts
    }

//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): CartRecViewAdapter.ViewHolder {
//        return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.row_cart,parent,false),mListener)
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartRecViewAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.row_cart,parent,false),mListener)
    }

    class ViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        var cartProductsImage = itemView.findViewById<ImageView>(R.id.cartProductsImage)
        var cartProductsTitle = itemView.findViewById<TextView>(R.id.cartProductsTitle)
        var cartProductsPrice = itemView.findViewById<TextView>(R.id.cartProductsPrice)
        var btnAddItem = itemView.findViewById<ImageButton>(R.id.btnAddItem)
        var btnRemoveItem = itemView.findViewById<ImageButton>(R.id.btnRemoveItem)
        var itemCount = itemView.findViewById<TextView>(R.id.itemCount)
    }

    override fun onBindViewHolder(holder: CartRecViewAdapter.ViewHolder, position: Int) {
//        holder.cartProductsTitle.text = arrProducts?.get(position)?.title
//        holder.btnAddItem.setOnClickListener(){
//            var count = Integer.parseInt(holder.itemCount.text.toString())
//            holder.itemCount.text= (count+1).toString()
//        }
//        holder.btnRemoveItem.setOnClickListener(){
//            var count = Integer.parseInt(holder.itemCount.text.toString())
//            if (count>0){
//                holder.itemCount.text= (count-1).toString()
//            }
//        }
        holder.itemCount.text = arrProducts?.get(position)?.count.toString()
        holder.cartProductsTitle.text = arrProducts?.get(position)?.product?.title.toString()
        holder.cartProductsPrice.text = ("$" + arrProducts?.get(position)?.product?.price) ?: ""
        Glide.with(context!!).load(arrProducts!![position].product?.image)
            .into(holder.cartProductsImage)
    }

    override fun getItemCount(): Int {
        return arrProducts!!.size
    }


    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun onItemClickListener(listener: onItemClickListener)
    {
        mListener = listener
    }

}