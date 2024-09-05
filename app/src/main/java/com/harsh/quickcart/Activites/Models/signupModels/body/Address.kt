package com.harsh.quickcart.Activites.Models.signupModels.body


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    var city: String?,
    @SerializedName("geolocation")
    var geolocation: Geolocation?,
    @SerializedName("number")
    var number: Int?,
    @SerializedName("street")
    var street: String?,
    @SerializedName("zipcode")
    var zipcode: String?
)