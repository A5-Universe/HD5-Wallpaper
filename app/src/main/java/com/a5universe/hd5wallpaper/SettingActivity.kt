package com.a5universe.hd5wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a5universe.hd5wallpaper.databinding.ActivityCategoryBinding
import com.a5universe.hd5wallpaper.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
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