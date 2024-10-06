package com.harsh.quickcart.Activites.Models.productsModels


import com.google.gson.annotations.SerializedName

data class GetSingleProduct(
    @SerializedName("category")
    var category: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("price")
    var price: Double?,
    @SerializedName("rating")
    var rating: RatingX?,
    @SerializedName("title")
    var title: String?
)