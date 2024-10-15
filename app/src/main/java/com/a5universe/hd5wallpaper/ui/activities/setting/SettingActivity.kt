package com.a5universe.hd5wallpaper.ui.activities.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.a5universe.hd5wallpaper.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.profile.setOnClickListener {
            Toast.makeText(this@SettingActivity, "Profile is coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.coin.setOnClickListener {
            Toast.makeText(this@SettingActivity, "Coin is coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.reward.setOnClickListener {
            Toast.makeText(this@SettingActivity, "Reward is coming soon", Toast.LENGTH_SHORT).show()
        }
        binding.wishList.setOnClickListener {
            Toast.makeText(this@SettingActivity, "WishList is coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.language.setOnClickListener {
            Toast.makeText(this@SettingActivity, "Language is coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.notification.setOnClickListener {
            Toast.makeText(this@SettingActivity, "Notification is coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.help.setOnClickListener {
            Toast.makeText(this@SettingActivity, "Help is coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.about.setOnClickListener {
            Toast.makeText(this@SettingActivity, "About is coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.invite.setOnClickListener {
            Toast.makeText(this@SettingActivity, "Invite is coming soon", Toast.LENGTH_SHORT).show()
        }


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