package com.a5universe.hd5wallpaper


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.a5universe.hd5wallpaper.Fragments.DownloadFragment
import com.a5universe.hd5wallpaper.Fragments.HomeFragment
import com.a5universe.hd5wallpaper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // home fragment auto loaded
        replaceFragment(HomeFragment())

        // home btn click event
        binding.icHome.setOnClickListener{
            val existingFragment =supportFragmentManager.findFragmentById(R.id.fragmentReplace)
            if (existingFragment == null || existingFragment.javaClass != HomeFragment::class.java){
                replaceFragment(HomeFragment())  // home fragment replace
            }
        }

        // download btn click event
        binding.icDownload.setOnClickListener{
            val existingFragment =supportFragmentManager.findFragmentById(R.id.fragmentReplace)
            if (existingFragment == null || existingFragment.javaClass != DownloadFragment::class.java){
                replaceFragment(DownloadFragment())  // download fragment replace
            }
        }


        // setting btn click event
        binding.icSetting.setOnClickListener{
            startActivity(Intent(this, SettingActivity::class.java))
        }

        // live wallpaper btn click event
        binding.icLive.setOnClickListener{
            startActivity(Intent(this, liveWallpaper::class.java))
        }

        // search btn work
        binding.icSearch.setOnClickListener {
            // search popup window
        }



    }
    //method for replacement fragment...
    private fun replaceFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentReplace,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //on back press method
    override fun onBackPressed() {
        // If the back button is pressed, exit the app
        moveTaskToBack(true)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(1)
    }
}