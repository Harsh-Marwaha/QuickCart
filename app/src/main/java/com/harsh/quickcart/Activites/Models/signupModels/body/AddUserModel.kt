package com.harsh.quickcart.Activites.Models.signupModels.body


import com.google.gson.annotations.SerializedName

data class AddUserModel(
    @SerializedName("avatar")
    var avatar: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("password")
    var password: String?
)