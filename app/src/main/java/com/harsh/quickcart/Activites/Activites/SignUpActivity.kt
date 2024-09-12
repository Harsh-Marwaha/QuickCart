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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.signupModels.body.AddUserModel
import com.harsh.quickcart.Activites.Models.signupModels.response.SignUpResponseModel
//import com.harsh.quickcart.Activites.Apis.Store_Service
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random


class SignUpActivity : AppCompatActivity() {
private val TAG =SignUpActivity::class.java.simpleName
    var btnContinueSignUp : Button? = null
    var loadingPB: ProgressBar? = null
    var edtName : EditText? = null
    var edtEmail : EditText? = null
    var edtPassword : EditText? = null
    var edtConfirmPassword : EditText? = null

    private var db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        btnContinueSignUp = findViewById(R.id.btnContinueSignUp)
        loadingPB = findViewById(R.id.idLoadingPB);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        btnContinueSignUp?.setOnClickListener() {

            loadingPB?.visibility = View.VISIBLE

            if (edtName?.text.toString().isEmpty() || edtEmail?.text.toString().isEmpty() || edtPassword?.text.toString().isEmpty() || edtConfirmPassword?.text.toString().isEmpty()){

                Toast.makeText(applicationContext,"Please fill all details",Toast.LENGTH_SHORT).show()
                loadingPB?.visibility = View.GONE

            }
            else{
                if (edtPassword?.text.toString()==edtConfirmPassword?.text.toString()){
//                    postData(edtName!!.getText().toString(), edtEmail!!.getText().toString(), edtPassword!!.getText().toString())
                    firebaseAuth = FirebaseAuth.getInstance()

                    firebaseAuth.createUserWithEmailAndPassword(edtEmail?.text.toString(),edtPassword?.text.toString()).addOnCompleteListener{
                        if (it.isSuccessful){
                            val userMap = hashMapOf(
                                "name" to edtName?.text.toString(),
                                "email" to edtEmail?.text.toString(),
                                "password" to edtPassword?.text.toString()
                            )

                            val userId = FirebaseAuth.getInstance().currentUser?.uid

                            if (userId != null) {
                                db.collection("Users").document(userId).set(userMap).addOnSuccessListener {
//                                    Toast.makeText(applicationContext,"Welcome ${edtName?.text.toString()}",Toast.LENGTH_SHORT).show()
                                    edtName?.text?.clear()
                                    edtEmail?.text?.clear()
                                    edtPassword?.text?.clear()
                                    edtConfirmPassword?.text?.clear()

                                    loadingPB?.visibility = View.GONE

                                    val intent = Intent(applicationContext,MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }.addOnFailureListener{
                                    Toast.makeText(applicationContext,"Sign Up Failed",Toast.LENGTH_SHORT).show()
                                    loadingPB?.visibility = View.GONE
                                }
                            }
                        }
                        else{
                            Toast.makeText(applicationContext,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener{
                        Toast.makeText(applicationContext,"Failed to add user",Toast.LENGTH_SHORT).show()
                        loadingPB?.visibility = View.GONE
                    }

                }

                else{
                    Toast.makeText(applicationContext,"Password and Confirm Password Should be same",Toast.LENGTH_SHORT).show()
                    loadingPB?.visibility = View.GONE
                }
            }

        }



    }

//    private fun postData(name : String, email : String, password : String) {
//
//        loadingPB?.visibility = View.VISIBLE
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.escuelajs.co/api/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val retrofitAPI = retrofit.create(StoreService::class.java)
//        val modal = AddUserModel("https://femina.wwmindia.com/thumb/content/2016/Jun/thumbnail_1464861339.jpg?width=1200&height=900",email,name,password)
//        val call: Call<SignUpResponseModel> = retrofitAPI.addUser(modal)
//
//        call.enqueue(object : Callback<SignUpResponseModel> {
//            override fun onResponse(call: Call<SignUpResponseModel>, response: Response<SignUpResponseModel>) {
//                Log.d(TAG, "postData: onResponse: ${Gson().toJson(response.body())}")
//                Toast.makeText(applicationContext, "Data added to API", Toast.LENGTH_SHORT).show()
//
//                loadingPB?.visibility = View.GONE
//                edtName?.setText("")
//                edtEmail?.setText("")
//                edtPassword?.setText("")
//                edtConfirmPassword?.setText("")
//
//                val intent = Intent(applicationContext,HomeActivity::class.java)
//                startActivity(intent)
//
//            }
//
//            override fun onFailure(call: Call<SignUpResponseModel>, t: Throwable) {
//
//                Toast.makeText(applicationContext,"Error found is : ${t.message}",Toast.LENGTH_SHORT).show()
//
//            }
//        })
//    }

}