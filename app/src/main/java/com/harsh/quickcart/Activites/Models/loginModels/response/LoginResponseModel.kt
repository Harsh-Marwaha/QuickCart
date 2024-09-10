package com.harsh.quickcart.Activites.Models.loginModels.response


import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("refresh_token")
    var refreshToken: String?
)