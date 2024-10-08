package com.harsh.quickcart.Activites.Apis

import android.content.SharedPreferences
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetCategories
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetSingleCategory
import com.harsh.quickcart.Activites.Models.CategoriesModels.GetSingleCategoryItem
import com.harsh.quickcart.Activites.Models.ProfileModels.response.ProfileResponseModel
import com.harsh.quickcart.Activites.Models.loginModels.body.UserLoginModel
import com.harsh.quickcart.Activites.Models.loginModels.response.LoginResponseModel
import com.harsh.quickcart.Activites.Models.productsModels.GetProducts
import com.harsh.quickcart.Activites.Models.productsModels.GetProductsItem
import com.harsh.quickcart.Activites.Models.productsModels.GetSingleProduct
import com.harsh.quickcart.Activites.Models.signupModels.body.AddUserModel
import com.harsh.quickcart.Activites.Models.signupModels.response.SignUpResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

//import retrofit2.Call


const val BASE_URL = "https://api.escuelajs.co/api/v1/"

interface StoreService {

    var getSharedPreferences : SharedPreferences
//    var x = getSharedPreferences.getString("accessToken","_")

    @Headers("Authorization: Bearer")

    @POST("users/")
    fun addUser (@Body addUserModal: AddUserModel?): retrofit2.Call<SignUpResponseModel>

    @POST("auth/login/")
    fun login(@Body userLoginModel: UserLoginModel?): Call<LoginResponseModel>

    @GET("auth/profile")
    fun getProfile(@Header("Authorization") authToken: String): Call<ProfileResponseModel>

    @GET("products")
    fun getProducts(): Call<GetProducts>

    @GET("products/{id}")
    fun getProduct(@Path("id") id: Int): Call<GetSingleProduct>

    @GET("products/categories")
    fun getCategories(): Call<GetCategories>

    @GET("{id}")
    fun getSingleCategory(@Path("id") id: String): Call<GetSingleCategory>

//    @GET("{id}")
//    fun getSingleCategoryItem(@Path("id") id: String): Call<List<GetSingleCategoryItem>>
//    @GET("categories/1/products")
//    fun getCategoriesProducts(): Call<GetCategories>
}