package com.harsh.quickcart.Activites.Activites

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import com.harsh.quickcart.Activites.Apis.StoreService
import com.harsh.quickcart.Activites.Models.ProfileModels.response.ProfileResponseModel
import com.harsh.quickcart.Activites.Models.loginModels.body.UserLoginModel
import com.harsh.quickcart.Activites.Models.loginModels.response.LoginResponseModel
//import com.harsh.quickcart.Activites.Models.signupModels.response.LoginResponseModel
import com.harsh.quickcart.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    var edtUserEmail : EditText? = null
    var edtPass : EditText? = null
    var btnContinue : Button? = null
    var btnGoogle : Button? = null
    var btnFacebook : Button? = null
    var btnSignUp : Button? = null
    var loadingPB: ProgressBar? = null
    lateinit var getSharedPreferences : SharedPreferences
    private var db = Firebase.firestore

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtUserEmail = findViewById(R.id.edtUserEmail)
        edtPass = findViewById(R.id.edtPass)
        btnContinue = findViewById(R.id.btnContinue)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)
        btnSignUp = findViewById(R.id.btnSignUp)
        loadingPB = findViewById(R.id.idLoadingPB)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }

//        getSharedPreferences=getSharedPreferences("login", MODE_PRIVATE)
//
//        var accessToken =getSharedPreferences.getString("accessToken","_")
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.escuelajs.co/api/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val retrofitAPI = retrofit.create(StoreService::class.java)
//
//        val modal = ProfileModel(accessToken)
//        val call: Call<ProfileResponseModel> = retrofitAPI.getProfile("Bearer $accessToken")
//
//        Log.d(TAG, "AccessToken: $accessToken")
//
//        call.enqueue(object : Callback<ProfileResponseModel>{
//            override fun onResponse(
//                p0: Call<ProfileResponseModel>,
//                p1: Response<ProfileResponseModel>
//            ) {
//                if(p1.body()!=null){
//                    Log.d(TAG, "postLogin : onResponse: ${Gson().toJson(p1.body())}")
//                    Toast.makeText(applicationContext, "Welcome ${p1.body()?.name}", Toast.LENGTH_SHORT).show()
//
//                    val intent = Intent(applicationContext,HomeActivity::class.java)
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(applicationContext,"Invalid Login Credentials",Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(p0: Call<ProfileResponseModel>, p1: Throwable) {
//                Log.d(TAG, "postLogin : onResponse: ${p1.message.toString()}")
//                Toast.makeText(applicationContext,"Error found is : ${p1.message}",Toast.LENGTH_SHORT).show()
//            }
//
//        })
        
        btnSignUp?.setOnClickListener(){
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        btnContinue?.setOnClickListener(){
            loadingPB?.visibility = View.VISIBLE

            if (edtUserEmail?.text.toString().isEmpty() || edtPass?.text.toString().isEmpty()){
                Toast.makeText(applicationContext,"Please enter both Username and Password",Toast.LENGTH_SHORT).show()
                loadingPB?.visibility = View.GONE
            }
            else{
//                postLogin(edtUserEmail?.text.toString(),edtPass?.text.toString())
                firebaseAuth = FirebaseAuth.getInstance()

                firebaseAuth.signInWithEmailAndPassword(edtUserEmail?.text.toString(),edtPass?.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        loadingPB?.visibility = View.GONE
                        val intent = Intent(applicationContext,HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(applicationContext,"Log In Failed",Toast.LENGTH_SHORT).show()
                        loadingPB?.visibility = View.GONE
                    }
                }.addOnFailureListener {
                    Toast.makeText(applicationContext,it.message.toString(),Toast.LENGTH_SHORT).show()
                    loadingPB?.visibility = View.GONE
                }

            }
        }

        btnGoogle?.setOnClickListener(){
            googleSignIn()
        }

    }

    private fun postLogin(username : String, password : String) {

            loadingPB?.visibility = View.VISIBLE

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.escuelajs.co/api/v1/")
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

                    edtUserEmail?.setText("")
                    edtPass?.setText("")

                    var accessToken = response.body()?.accessToken.toString()
                    var refreshToken = response.body()?.refreshToken.toString()

                    var sharedPreferences : SharedPreferences
                    sharedPreferences=getSharedPreferences("login", MODE_PRIVATE)

                    var editor : SharedPreferences.Editor
                    editor =sharedPreferences.edit()

                    editor.putString("accessToken",accessToken).apply()
                    editor.putString("refreshToken",refreshToken).apply()


                    val intent = Intent(applicationContext,HomeActivity::class.java)
                    startActivity(intent)
                    finish()

                }

                override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {

                    Log.d(TAG, "postLogin : onResponse: ${t.message.toString()}")
                    Toast.makeText(applicationContext,"Error found is : ${t.message}",Toast.LENGTH_SHORT).show()

                }
            })
    }

    private fun googleSignIn() {
        loadingPB?.visibility = View.VISIBLE

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onActivityResult: ${e.message.toString()}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT)
                        .show()
                    val userMap = hashMapOf(
                        "name" to user?.displayName.toString(),
                        "email" to user?.email.toString(),
                        "uid" to user?.uid.toString()
                    )

                    val userId = FirebaseAuth.getInstance().currentUser?.uid

                    if (userId != null) {
                        db.collection("Users").document(userId).set(userMap)
                            .addOnSuccessListener {
//                                    Toast.makeText(applicationContext,"Welcome ${edtName?.text.toString()}",Toast.LENGTH_SHORT).show()
                            edtUserEmail?.text?.clear()
                            edtPass?.text?.clear()

                            loadingPB?.visibility = View.GONE

                        }
                            .addOnFailureListener {
                            Toast.makeText(applicationContext, "Log In Failed", Toast.LENGTH_SHORT)
                                .show()
                            loadingPB?.visibility = View.GONE
                        }
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        loadingPB?.visibility = View.GONE
                    }
                }
            }

    }
}