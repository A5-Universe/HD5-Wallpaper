package com.a5universe.hd5wallpaper.ui.activities.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.a5universe.hd5wallpaper.data.BestOfMonthModel
import com.a5universe.hd5wallpaper.data.CategoryModel
import com.a5universe.hd5wallpaper.ui.adapter.BestOfMonthAdapter
import com.a5universe.hd5wallpaper.ui.adapter.CategoryAdapter
import com.a5universe.hd5wallpaper.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var originalListOfCategory: List<CategoryModel>
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        db = FirebaseFirestore.getInstance()

        setupBestOfMonthRecyclerView()
        setupCategoryRecyclerView()
        setupSearchViewListener()

        return binding.root
    }

    // method for toggle searchbar
    fun toggleSearchBarVisibility() {
        val searchView = binding.searchView
        if (searchView.visibility == View.VISIBLE) {
        searchView.visibility = View.GONE
        } else {
        searchView.visibility = View.VISIBLE
    }
}


    // method for trending
    private fun setupBestOfMonthRecyclerView() {
        // ... (your existing code)
        db.collection("bestofmonth").addSnapshotListener { value, error ->
            val listBestOfMonth = arrayListOf<BestOfMonthModel>()
            val data = value?.toObjects(BestOfMonthModel::class.java)
            listBestOfMonth.addAll(data!!)

            binding.rcvBom.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcvBom.adapter = BestOfMonthAdapter(requireContext(), listBestOfMonth)
        }
    }

    // method for category recycler view
    private fun setupCategoryRecyclerView() {
        db.collection("categories").addSnapshotListener { value, error ->
            val listOfCategory = arrayListOf<CategoryModel>()
            val data = value?.toObjects(CategoryModel::class.java)
            listOfCategory.addAll(data!!)

            originalListOfCategory = listOfCategory.toList()
            categoryAdapter = CategoryAdapter(requireContext(), originalListOfCategory)

            binding.rcvCategory.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rcvCategory.adapter = categoryAdapter
        }

    }

    // method for search view listener
    private fun setupSearchViewListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterCategories(it) }
                return true
            }
        })
    }

    private fun filterCategories(query: String) {
        categoryAdapter.filter(query)
    }

}