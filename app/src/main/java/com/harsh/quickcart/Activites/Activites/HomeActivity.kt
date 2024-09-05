package com.harsh.quickcart.Activites.Activites

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.harsh.quickcart.Activites.Fragments.CartFragment
import com.harsh.quickcart.Activites.Fragments.CategoriesFragment
import com.harsh.quickcart.Activites.Fragments.HomeFragment
import com.harsh.quickcart.Activites.Fragments.ProfileFragment
import com.harsh.quickcart.R

class HomeActivity : AppCompatActivity() {

    var frameLayout : FrameLayout? = null
    var bottomNavigationView : BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        var navHome = HomeFragment()
        var navCart = CartFragment()
        var navCategories = CategoriesFragment()
        var navProfile = ProfileFragment()

//        var btnNavHome  = findViewById(R.id.navHome)

        frameLayout = findViewById(R.id.frameLayout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

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