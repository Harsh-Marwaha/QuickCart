package com.harsh.quickcart.Activites.Models.signupModels.response


import com.google.gson.annotations.SerializedName

data class Geolocation(
    @SerializedName("lat")
    var lat: String?,
    @SerializedName("long")
    var long: String?
)