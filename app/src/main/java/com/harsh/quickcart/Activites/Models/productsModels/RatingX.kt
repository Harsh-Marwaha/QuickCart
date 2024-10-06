package com.harsh.quickcart.Activites.Models.productsModels


import com.google.gson.annotations.SerializedName

data class RatingX(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("rate")
    var rate: Double?
)