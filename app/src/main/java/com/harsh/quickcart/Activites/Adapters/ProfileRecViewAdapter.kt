package com.harsh.quickcart.Activites.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter.ViewHolder
import com.harsh.quickcart.Activites.Models.ProfileModels.ProfileModel
import com.harsh.quickcart.R

class ProfileRecViewAdapter : RecyclerView.Adapter<ProfileRecViewAdapter.ViewHolder> {

    private var context : Context? = null
    private var arrData : ArrayList<ProfileModel>? = null

    constructor(context: Context,arrData : ArrayList<ProfileModel>?){
        this.context = context
        this.arrData = arrData
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var ivSettings = itemView.findViewById<ImageView>(R.id.ivSettings)
        var tvSettings = itemView.findViewById<TextView>(R.id.tvSettings)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileRecViewAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context!!).inflate(R.layout.row_profile,parent,false))
    }

    override fun onBindViewHolder(holder: ProfileRecViewAdapter.ViewHolder, position: Int) {
        holder.ivSettings.setImageResource(arrData?.get(position)?.image!!)
        holder.tvSettings.text = arrData!!.get(position).title
    }

    override fun getItemCount(): Int {
        return arrData!!.size
    }
}