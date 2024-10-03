package com.harsh.quickcart.Activites.Models.productsModels


import com.google.gson.annotations.SerializedName

data class GetProductsItem(
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
    var rating: Rating?,
    @SerializedName("title")
    var title: String?
)