package com.harsh.quickcart.Activites.Models.CartModels

import java.io.Serializable

data class CartModel(var uid:String? = null, var product: Any? = null, var productId: Any? = null, var count: Any? = null) : Serializable