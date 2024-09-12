package com.harsh.quickcart.Activites.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.harsh.quickcart.Activites.Activites.MainActivity
import com.harsh.quickcart.R


class ProfileFragment : Fragment() {

    var btnOrders : Button? = null
    var btnCoupons : Button? = null
    var btnWishlist : Button? = null
    var btnHelpCenter : Button? = null
    var btnLogOut : Button? = null

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnOrders = view.findViewById(R.id.btnOrders)
        btnCoupons = view.findViewById(R.id.btnCoupons)
        btnWishlist = view.findViewById(R.id.btnWishlist)
        btnHelpCenter = view.findViewById(R.id.btnHelpCenter)
        btnLogOut = view.findViewById(R.id.btnLogOut)

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


        btnOrders?.setOnClickListener(){
            replaceFragment(OrdersFragment())
        }

        btnCoupons?.setOnClickListener(){
            replaceFragment(CouponsFragment())
        }

        btnWishlist?.setOnClickListener(){
            replaceFragment(WishlistFragment())
        }

        btnHelpCenter?.setOnClickListener(){
            replaceFragment(HelpCenterFragment())
        }

        btnLogOut?.setOnClickListener(){
            mAuth.signOut()

            mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
                // Optional: Update UI or show a message to the user
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

    }

    fun replaceFragment(fragment : Fragment){
        val fm = fragmentManager?.beginTransaction()
        val replace = fm?.replace(R.id.frameLayout,fragment)
        replace?.commit()
    }

}