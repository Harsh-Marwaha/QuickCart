package com.harsh.quickcart.Activites.Models.loginModels.body


import com.google.gson.annotations.SerializedName

data class UserLoginModel(
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?
)