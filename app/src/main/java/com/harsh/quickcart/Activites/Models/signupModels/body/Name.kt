package com.harsh.quickcart.Activites.Models.signupModels.body


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("firstname")
    var firstname: String?,
    @SerializedName("lastname")
    var lastname: String?
)