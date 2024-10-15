package com.a5universe.hd5wallpaper.ui.activities.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.a5universe.hd5wallpaper.data.BestOfMonthModel
import com.a5universe.hd5wallpaper.ui.adapter.CategoryImagesAdapter
import com.a5universe.hd5wallpaper.databinding.ActivityCategoryBinding
import com.google.firebase.firestore.FirebaseFirestore

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //for back button click
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }


        val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid") // key abstract
        val name = intent.getStringExtra("name")


        db.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener{value,error->

            val listOfCatWallpaper = arrayListOf<BestOfMonthModel>()
            val data = value?.toObjects(BestOfMonthModel::class.java)
            listOfCatWallpaper.addAll(data!!)

            binding.catTitle.text = name.toString()

            binding.catRcv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            binding.catRcv.adapter = CategoryImagesAdapter(this,listOfCatWallpaper)
        }

    }



    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}