package com.harsh.quickcart.Activites.Models.productsModels


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("rate")
    var rate: Double?
)