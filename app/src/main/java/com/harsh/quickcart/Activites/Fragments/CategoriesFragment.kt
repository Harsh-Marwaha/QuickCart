package com.harsh.quickcart.Activites.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harsh.quickcart.Activites.Adapters.CategoriesRecViewAdapter
import com.harsh.quickcart.Activites.Models.CategoriesModel
import com.harsh.quickcart.R


class CategoriesFragment : Fragment() {

    var recViewCategories : RecyclerView? = null
    var categoriesRecViewAdapter : CategoriesRecViewAdapter? = null
    var arrCategories : ArrayList<CategoriesModel>? = ArrayList()
    var categories : CategoriesModel? = CategoriesModel()
    var arrCategoriesImg : ArrayList<String>? = ArrayList()
    var arrCategoriesTitle : ArrayList<String>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrCategoriesImg?.add("https://3.imimg.com/data3/TB/KQ/MY-238885/videocon-ha.jpg")
        arrCategoriesImg?.add("https://www.avtaara.com/wp-content/uploads/2021/01/20210105_163052_0000.png")
        arrCategoriesImg?.add("https://cms.cloudinary.vpsvc.com/image/upload/c_scale,dpr_auto,f_auto,q_auto:best,t_productPageHeroGalleryTransformation_v2,w_auto/India%20LOB/Clothing%20and%20Bags/Men's%20Basic%20Cotton%20T-Shirts/IN_Mens-T-Shirts_02")
        arrCategoriesImg?.add("https://qph.cf2.quoracdn.net/main-qimg-ee3dbe18ad936b8a2b83d84fc1ff3f3e-lq")

        arrCategoriesTitle?.add("Electronics")
        arrCategoriesTitle?.add("Jewelery")
        arrCategoriesTitle?.add("Men's clothing")
        arrCategoriesTitle?.add("Women's clothing")

        setData()

        recViewCategories = view.findViewById(R.id.recViewCategories)
        categoriesRecViewAdapter =  CategoriesRecViewAdapter(context,arrCategories)
        recViewCategories?.adapter = categoriesRecViewAdapter
        recViewCategories?.layoutManager = GridLayoutManager(context,2)

    }

    private fun setData()
    {
        for (i in arrCategoriesTitle!!.indices)
        {
            val cat = CategoriesModel(arrCategoriesTitle?.get(i),arrCategoriesImg?.get(i))
            arrCategories?.add(cat)

        }
    }

}