package com.harsh.quickcart.Activites.Models.signupModels.body


import com.google.gson.annotations.SerializedName

data class AddUserModel(
    @SerializedName("address")
    var address: Address?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: Name?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("username")
    var username: String?
)