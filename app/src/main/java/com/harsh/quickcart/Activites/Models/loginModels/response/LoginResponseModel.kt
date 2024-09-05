package com.harsh.quickcart.Activites.Models.loginModels.response


import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("token")
    var token: String?
)