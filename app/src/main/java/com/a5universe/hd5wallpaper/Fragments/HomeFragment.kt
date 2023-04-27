package com.a5universe.hd5wallpaper.Fragments

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.a5universe.hd5wallpaper.Adapter.BomAdapter
import com.a5universe.hd5wallpaper.Adapter.CategoryAdapter
import com.a5universe.hd5wallpaper.Model.BomModel
import com.a5universe.hd5wallpaper.Model.categoryModel
import com.a5universe.hd5wallpaper.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        db = FirebaseFirestore.getInstance()

        db.collection("bestofmonth").addSnapshotListener{value,error->
            val listBestOfMonth = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listBestOfMonth.addAll(data!!)

            binding.rcvBom.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            binding.rcvBom.adapter = BomAdapter(requireContext(),listBestOfMonth)
        }


        db.collection("categories").addSnapshotListener{value,error->
            val listOfCategory = arrayListOf<categoryModel>()
            val data = value?.toObjects(categoryModel::class.java)
            listOfCategory.addAll(data!!)

            binding.rcvCategory.layoutManager= GridLayoutManager(requireContext(),2)
            binding.rcvCategory.adapter = CategoryAdapter(requireContext(),listOfCategory)
        }


        return binding.root
    }


}