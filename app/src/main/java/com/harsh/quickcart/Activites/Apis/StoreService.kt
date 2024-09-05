package com.harsh.quickcart.Activites.Apis

import com.harsh.quickcart.Activites.Models.loginModels.body.UserLoginModel
import com.harsh.quickcart.Activites.Models.loginModels.response.LoginResponseModel
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.Activites.Models.productsModels.GetProductsItem
import com.harsh.quickcart.Activites.Models.signupModels.body.AddUserModel
import com.harsh.quickcart.Activites.Models.signupModels.response.SignUpResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
//import retrofit2.Call


const val BASE_URL = "https://fakestoreapi.com/"

interface StoreService {

    @POST("users")
    fun addUser (@Body addUserModal: AddUserModel?): retrofit2.Call<SignUpResponseModel>

    @POST("auth/login")
    fun login(@Body userLoginModel: UserLoginModel?): Call<LoginResponseModel>

    @GET("products")
    fun getProducts(): Call<GetProducts>
}


//    var storeInstance : StoreService
//    init {
//        var retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
//            .build()
//        storeInstance = retrofit.create(StoreService::class.java)
//    }
