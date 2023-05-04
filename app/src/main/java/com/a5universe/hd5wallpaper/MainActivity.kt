package com.a5universe.hd5wallpaper


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.a5universe.hd5wallpaper.Fragments.DownloadFragment
import com.a5universe.hd5wallpaper.Fragments.HomeFragment
import com.a5universe.hd5wallpaper.Fragments.OfflineFragment
import com.a5universe.hd5wallpaper.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //internet check
        if (checkForInternet(this)) {
            // internet connect home fragment auto loaded
            replaceFragment(HomeFragment())
        } else {
            // internet disconnect download fragment auto loaded
            replaceFragment(DownloadFragment())
        }


        // home btn click event
        binding.icHome.setOnClickListener{
            val existingFragment =supportFragmentManager.findFragmentById(R.id.fragmentReplace)
            if (existingFragment == null || existingFragment.javaClass != HomeFragment::class.java){
                if (checkForInternet(this)) {
                    // internet connect home fragment show
                    replaceFragment(HomeFragment())
                } else {
                    // internet disconnect offline show
                    replaceFragment(OfflineFragment())
                }
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

            if (checkForInternet(this)) {
                // internet connect live wallpaper show
                startActivity(Intent(this, liveWallpaper::class.java))
            } else {
                // internet disconnect offline show
                replaceFragment(OfflineFragment())
            }
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


    //check internet connection...
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