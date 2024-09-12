package com.harsh.quickcart.Activites.Activites

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.harsh.quickcart.Activites.Fragments.CartFragment
import com.harsh.quickcart.Activites.Fragments.CategoriesFragment
import com.harsh.quickcart.Activites.Fragments.HomeFragment
import com.harsh.quickcart.Activites.Fragments.ProfileFragment
import com.harsh.quickcart.R

class HomeActivity : AppCompatActivity() {

    var frameLayout : FrameLayout? = null
    var bottomNavigationView : BottomNavigationView? = null

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        frameLayout = findViewById(R.id.frameLayout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        var auth = FirebaseAuth.getInstance()

        val userId = auth.currentUser?.uid

        if (userId != null){
            val ref = db.collection("Users").document(userId)
            ref.get().addOnSuccessListener {
                if (it!=null){
                    val name = it.data?.get("name")?.toString()
                    Toast.makeText(applicationContext,"Welcome $name",Toast.LENGTH_SHORT).show()
                }
            }
                .addOnFailureListener{
                    Toast.makeText(applicationContext,"Failed to get user name from database",Toast.LENGTH_SHORT).show()
                }
        }
        else{
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        replaceFragment(HomeFragment())

        bottomNavigationView?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navHome->{
                    replaceFragment(HomeFragment())
                }
                R.id.navCart->{
                    replaceFragment(CartFragment())
                }
                R.id.navCategories->{
                    replaceFragment( CategoriesFragment())
                }
                R.id.navProfile->{
                    replaceFragment(ProfileFragment())
                }
            }
            true
        }

    }

    fun replaceFragment(fragment : Fragment){
        val fm = supportFragmentManager.beginTransaction()
        val replace = fm.replace(R.id.frameLayout,fragment)
        replace.commit()
    }

}