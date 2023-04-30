package com.a5universe.hd5wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a5universe.hd5wallpaper.databinding.ActivityLiveWallpaperBinding
import com.a5universe.hd5wallpaper.databinding.ActivitySettingBinding

class liveWallpaper : AppCompatActivity() {

    lateinit var binding: ActivityLiveWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveWallpaperBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_live_wallpaper)

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