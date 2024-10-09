package com.harsh.quickcart.Activites.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.harsh.quickcart.Activites.Activites.MainActivity
import com.harsh.quickcart.Activites.Adapters.HomeRecViewAdapter
import com.harsh.quickcart.Activites.Adapters.ProfileRecViewAdapter
import com.harsh.quickcart.Activites.Models.ProfileModels.ProfileModel
import com.harsh.quickcart.R


class ProfileFragment : Fragment() {

    var btnOrders : Button? = null
    var btnCoupons : Button? = null
    var btnWishlist : Button? = null
    var btnHelpCenter : Button? = null
    var btnLogOut : Button? = null
    var tvname : TextView? = null
    var loadingPB: LinearLayout? = null

    var arrSettings : ArrayList<ProfileModel> = ArrayList()
    var arrMyActivity : ArrayList<ProfileModel> = ArrayList()
    var recViewProfile : RecyclerView? = null
    var recViewMyActivity : RecyclerView? = null
    var profileRecViewAdapter : ProfileRecViewAdapter? = null

    private var db = Firebase.firestore
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
        tvname = view.findViewById(R.id.name)
        loadingPB = view.findViewById(R.id.idLoadingPB)
        recViewProfile = view.findViewById(R.id.recViewSettings)
        recViewMyActivity = view.findViewById(R.id.recViewMyActivity)

        loadingPB?.visibility = View.VISIBLE

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


        var auth = FirebaseAuth.getInstance()

        val userId = auth.currentUser?.uid

        if (userId != null){
            val ref = db.collection("Users").document(userId)
            ref.get().addOnSuccessListener {
                if (it!=null){
                    val name = it.data?.get("name")?.toString()
                    tvname?.text = "Welcome, "+name
                    loadingPB?.visibility = View.GONE
                }
            }
                .addOnFailureListener{
                    Toast.makeText(requireContext(),"Failed to get user name from database",
                        Toast.LENGTH_SHORT).show()
                }
        }

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

        var modal = ProfileModel(R.drawable.baseline_person_24,"Edit Profile")
        arrSettings.add(modal)
        modal = ProfileModel(R.drawable.wallet,"Saved Credit / Debit & Gift Cards")
        arrSettings.add(modal)
        modal = ProfileModel(R.drawable.location,"Saved Addresses")
        arrSettings.add(modal)
        modal = ProfileModel(R.drawable.language,"Select Language")
        arrSettings.add(modal)
        modal = ProfileModel(R.drawable.notification_settings,"Notification Settings")
        arrSettings.add(modal)

        modal = ProfileModel(R.drawable.reviews,"Reviews")
        arrMyActivity.add(modal)
        modal = ProfileModel(R.drawable.questions,"Questions & Answers")
        arrMyActivity.add(modal)

        profileRecViewAdapter= ProfileRecViewAdapter(requireContext(),arrSettings)
        recViewProfile?.adapter = profileRecViewAdapter
        recViewProfile?.layoutManager = LinearLayoutManager(requireContext())

        profileRecViewAdapter= ProfileRecViewAdapter(requireContext(),arrMyActivity)
        recViewMyActivity?.adapter = profileRecViewAdapter
        recViewMyActivity?.layoutManager = LinearLayoutManager(requireContext())
//        profileRecViewAdapter!!.notifyDataSetChanged()

    }

    fun replaceFragment(fragment : Fragment){
        val fm = fragmentManager?.beginTransaction()
        val replace = fm?.replace(R.id.frameLayout,fragment)
        replace?.commit()
    }

}