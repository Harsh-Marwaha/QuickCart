package com.harsh.quickcart.Activites.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.harsh.quickcart.R


class ProfileFragment : Fragment() {

    var btnOrders : Button? = null
    var btnCoupons : Button? = null
    var btnWishlist : Button? = null
    var btnHelpCenter : Button? = null

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

    }

    fun replaceFragment(fragment : Fragment){
        val fm = fragmentManager?.beginTransaction()
        val replace = fm?.replace(R.id.frameLayout,fragment)
        replace?.commit()
    }

}