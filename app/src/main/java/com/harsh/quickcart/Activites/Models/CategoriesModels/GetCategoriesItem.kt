package com.harsh.quickcart.Activites.Models.CategoriesModels


import com.google.gson.annotations.SerializedName

data class GetCategoriesItem(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("name")
    var name: String?
)