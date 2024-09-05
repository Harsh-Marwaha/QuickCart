package com.harsh.quickcart.Activites.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.harsh.quickcart.R


class WishlistFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wishlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                replaceFragment(ProfileFragment())
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callBack)

    }

    fun replaceFragment(fragment : Fragment){
        val fm = fragmentManager?.beginTransaction()
        val replace = fm?.replace(R.id.frameLayout,fragment)
        replace?.commit()
    }

}