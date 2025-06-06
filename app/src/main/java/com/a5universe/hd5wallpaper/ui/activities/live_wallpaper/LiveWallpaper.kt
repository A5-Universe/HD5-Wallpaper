package com.a5universe.hd5wallpaper.ui.activities.live_wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a5universe.hd5wallpaper.databinding.ActivityLiveWallpaperBinding

class LiveWallpaper : AppCompatActivity() {

    lateinit var binding: ActivityLiveWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for back button click
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}