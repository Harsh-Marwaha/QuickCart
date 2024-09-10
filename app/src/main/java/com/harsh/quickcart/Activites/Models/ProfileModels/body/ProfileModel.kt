package com.harsh.quickcart.Activites.Models.ProfileModels.body


import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("Authorization")
    var authorization: String?
)