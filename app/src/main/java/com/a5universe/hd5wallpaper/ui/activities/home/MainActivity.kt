package com.a5universe.hd5wallpaper.ui.activities.home


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.a5universe.hd5wallpaper.R
import com.a5universe.hd5wallpaper.databinding.ActivityMainBinding
import com.a5universe.hd5wallpaper.ui.activities.live_wallpaper.LiveWallpaper
import com.a5universe.hd5wallpaper.ui.activities.setting.SettingActivity
import com.a5universe.hd5wallpaper.ui.activities.home.fragments.DownloadFragment
import com.a5universe.hd5wallpaper.ui.activities.home.fragments.HomeFragment
import com.a5universe.hd5wallpaper.ui.activities.home.fragments.OfflineFragment
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), SearchClickListener {

    private lateinit var binding: ActivityMainBinding
    private var backPressedTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //internet check
        if (checkForInternet(this)) {
            // internet connected home fragment auto loaded
            replaceFragment(HomeFragment())
        }
        else {
            // internet disconnected download fragment auto loaded
            replaceFragment(DownloadFragment())
        }

        // home btn click event
        binding.icHome.setOnClickListener{

            val existingFragment = supportFragmentManager.findFragmentById(R.id.fragmentReplace)

            if (existingFragment == null || existingFragment.javaClass != HomeFragment::class.java) {
                if (checkForInternet(this)) {
                    // Internet connect home fragment show
                    replaceFragment(HomeFragment())
                    binding.icSearch.visibility = View.VISIBLE // Show the ic_search button
                } else {
                    // Internet disconnect offline show
                    replaceFragment(OfflineFragment())
                    binding.icSearch.visibility = View.GONE // Hide the ic_search button
                }
            } else {
                binding.icSearch.visibility = View.VISIBLE // Show the ic_search button if already on HomeFragment
            }

        }

        // download btn click event
        binding.icDownload.setOnClickListener{
            val existingFragment = supportFragmentManager.findFragmentById(R.id.fragmentReplace)

            if (existingFragment == null || existingFragment.javaClass != DownloadFragment::class.java){
                replaceFragment(DownloadFragment())  // DownloadFragment replace

                // Set ic_search button to be invisible
                binding.icSearch.visibility = View.GONE
            }

        }

        // setting btn click event
        binding.icSetting.setOnClickListener{
            startActivity(Intent(this, SettingActivity::class.java))
        }

        // live wallpaper btn click event
        binding.icLive.setOnClickListener{

            if (checkForInternet(this)) {
                // internet connect live wallpaper show
                startActivity(Intent(this, LiveWallpaper::class.java))
            } else {
                // internet disconnect offline show
                replaceFragment(OfflineFragment())
            }
        }

        //search btn click event
        binding.icSearch.setOnClickListener {
           // method call
            onSearchClicked()
        }
    }


    //   method for search clicked..
    override fun onSearchClicked() {
    val fragment = supportFragmentManager.findFragmentById(R.id.fragmentReplace) as? HomeFragment
    fragment?.toggleSearchBarVisibility()
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
        super.onBackPressed()
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            // If the back button is pressed, exit the app
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        } else {
            Toast.makeText(this, "Press back again to close the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }


    //check internet connection.
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}

// interface for SearchClickListener
interface SearchClickListener {
    fun onSearchClicked()
}