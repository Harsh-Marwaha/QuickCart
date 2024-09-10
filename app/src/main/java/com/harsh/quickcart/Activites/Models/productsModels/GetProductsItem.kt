package com.harsh.quickcart.Activites.Models.productsModels


import com.google.gson.annotations.SerializedName

data class GetProductsItem(
    @SerializedName("category")
    var category: Category?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("images")
    var images: List<String?>?,
    @SerializedName("price")
    var price: Int?,
    @SerializedName("title")
    var title: String?
)