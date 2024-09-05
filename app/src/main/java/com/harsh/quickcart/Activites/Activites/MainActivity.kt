package com.harsh.quickcart.Activites.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.loginModels.body.UserLoginModel
import com.harsh.quickcart.Activites.Models.loginModels.response.LoginResponseModel
import com.harsh.quickcart.Activites.Models.signupModels.body.AddUserModel
import com.harsh.quickcart.Activites.Models.signupModels.body.Address
import com.harsh.quickcart.Activites.Models.signupModels.body.Geolocation
import com.harsh.quickcart.Activites.Models.signupModels.body.Name
//import com.harsh.quickcart.Activites.Models.signupModels.response.LoginResponseModel
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    var edtUserName : EditText? = null
    var edtPass : EditText? = null
    var btnContinue : Button? = null
    var btnGoogle : Button? = null
    var btnFacebook : Button? = null
    var btnSignUp : Button? = null
    var loadingPB: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtUserName = findViewById(R.id.edtUserName)
        edtPass = findViewById(R.id.edtPass)
        btnContinue = findViewById(R.id.btnContinue)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)
        btnSignUp = findViewById(R.id.btnSignUp)
        loadingPB = findViewById(R.id.idLoadingPB)
        
        btnSignUp?.setOnClickListener(){
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        btnContinue?.setOnClickListener(){
            if (edtUserName?.text.toString().isEmpty() || edtPass?.text.toString().isEmpty()){
                Toast.makeText(applicationContext,"Please enter both Username and Password",Toast.LENGTH_SHORT).show()
            }
            else{
                postLogin(edtUserName?.text.toString(),edtPass?.text.toString())
            }
        }


    }

    private fun postLogin(username : String, password : String) {

            loadingPB?.visibility = View.VISIBLE

            val retrofit = Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val retrofitAPI = retrofit.create(StoreService::class.java)

            val modal = UserLoginModel(password,username)
            val call: Call<LoginResponseModel> = retrofitAPI.login(modal)

            call.enqueue(object : Callback<LoginResponseModel> {
                override fun onResponse(call: Call<LoginResponseModel>, response: Response<LoginResponseModel>) {
                    Log.d(TAG, "postLogin : onResponse: ${Gson().toJson(response.body())}")
                    Toast.makeText(applicationContext, "Welcome ${username}", Toast.LENGTH_SHORT).show()

                    loadingPB?.visibility = View.GONE

                    edtUserName?.setText("")
                    edtPass?.setText("")

                    val intent = Intent(applicationContext,HomeActivity::class.java)
                    startActivity(intent)

                }

                override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {

                    Log.d(TAG, "postLogin : onResponse: ${t.message.toString()}")
                    Toast.makeText(applicationContext,"Error found is : ${t.message}",Toast.LENGTH_SHORT).show()

                }
            })
        }

}