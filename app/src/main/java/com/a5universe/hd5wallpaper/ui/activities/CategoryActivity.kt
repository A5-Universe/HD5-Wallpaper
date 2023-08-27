package com.a5universe.hd5wallpaper.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.a5universe.hd5wallpaper.ui.adapter.CatImagesAdapter
import com.a5universe.hd5wallpaper.data.model.AdsModel
import com.a5universe.hd5wallpaper.data.model.BomModel
import com.a5universe.hd5wallpaper.R
import com.a5universe.hd5wallpaper.databinding.ActivityCategoryBinding
import com.google.android.gms.ads.*
import com.google.firebase.firestore.FirebaseFirestore

class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding
    lateinit var adView : AdView
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //for back button click
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        //for firebase app ads control
        firestore = FirebaseFirestore.getInstance()
        firestore.collection("Ads").document("hMpfXLkTbslnxunQei3C").addSnapshotListener{value,error->

            if (error != null){
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
            else{

              // AdsModel data = value.toObject(AdsModel.class);
                val data = value?.toObject(AdsModel::class.java)
                if (data?.adsStatus == false) {

                    //for ad method
                    loadBannerAd()

                }
            }
        }
        //...


        val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid") // key abstract
        val name = intent.getStringExtra("name")


        db.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener{value,error->

            val listOfCatWallpaper = arrayListOf<BomModel>()
            val data = value?.toObjects(BomModel::class.java)
            listOfCatWallpaper.addAll(data!!)

            binding.catTitle.text = name.toString()

            binding.catRcv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            binding.catRcv.adapter = CatImagesAdapter(this,listOfCatWallpaper)
        }

    }


    private fun loadBannerAd(){
        MobileAds.initialize(this) {}
        adView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(this@CategoryActivity, "Ad Loaded", Toast.LENGTH_SHORT).show()
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Toast.makeText(this@CategoryActivity, "Ad Closed", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }


    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}