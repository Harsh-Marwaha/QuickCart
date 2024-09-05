package com.harsh.quickcart.Activites.Models.loginModels.body


import com.google.gson.annotations.SerializedName

data class UserLoginModel(
    @SerializedName("password")
    var password: String?,
    @SerializedName("username")
    var username: String?
)