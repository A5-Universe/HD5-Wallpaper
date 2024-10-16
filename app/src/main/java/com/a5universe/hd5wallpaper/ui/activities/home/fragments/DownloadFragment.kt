package com.a5universe.hd5wallpaper.ui.activities.home.fragments


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.a5universe.hd5wallpaper.ui.adapter.CollectionAdapter
import com.a5universe.hd5wallpaper.databinding.FragmentDownloadBinding
import com.a5universe.hd5wallpaper.ui.activities.set_wallpaper.FinalWallpaper
import java.io.File

class DownloadFragment : Fragment() {

    private lateinit var binding: FragmentDownloadBinding
    private val STORAGE_PERMISSION_CODE = 101
    private lateinit var imageList: ArrayList<String>
    private lateinit var allFiles: Array<File>
    private val targetPath =
        Environment.getExternalStorageDirectory().absolutePath + "/Pictures/HD5 Wallpaper"
    private lateinit var adapter: CollectionAdapter

    // Declare a Handler and a Runnable
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadBinding.inflate(layoutInflater, container, false)


        imageList = arrayListOf()


        // Setup RecyclerView
        binding.rcvCollection.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = CollectionAdapter(requireContext(), imageList)
        binding.rcvCollection.adapter = adapter

        // Request storage permission
        if (requestPermission()) {
            loadImagesFromStorage()
        }


        // Setup auto-reload logic
        setupAutoReload() //test

        return binding.root
    }


    // Reload images when the fragment is resumed (to avoid reloading too early)
    override fun onResume() {
        super.onResume()
        if (requestPermission()) {
            loadImagesFromStorage() // Reload images after permission is granted
        }
    }

    // Auto-reload setup using Handler
    private fun setupAutoReload() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                loadImagesFromStorage()  // Reload the images from storage every time this runs
                handler.postDelayed(this, 5000)  // Check every 5 seconds (5000 ms)
            }
        }
        handler.post(runnable)  // Start the auto-reload
    }

    // Method to load images from storage
    private fun loadImagesFromStorage() {
        val targetFile = File(targetPath)
        if (targetFile.exists()) {
            allFiles = targetFile.listFiles() ?: emptyArray()
            imageList.clear() // Clear the old list before adding new items

            for (data in allFiles) {
                imageList.add(data.absolutePath)
            }

            // Update RecyclerView and show/hide "No Wallpapers" message
            if (imageList.isEmpty()) {
                binding.rcvCollection.visibility = View.GONE
                binding.tvNoWallpapers.visibility = View.VISIBLE
            } else {
                binding.rcvCollection.visibility = View.VISIBLE
                binding.tvNoWallpapers.visibility = View.GONE
            }

            adapter.notifyDataSetChanged() // Notify adapter to refresh the view
        } else {
            // If target directory does not exist, handle the case (e.g., show "No Wallpapers" message)
            binding.rcvCollection.visibility = View.GONE
            binding.tvNoWallpapers.visibility = View.VISIBLE
        }
    }

    //method of permission...
    private fun requestPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
            return false
        }

        return true
    }

    // Handle permission result //perfect
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
                loadImagesFromStorage() // Load images after permission is granted
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            }
        }
    }


    // Cancel auto-reload when the fragment is stopped
    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)  // Stop the auto-reload when fragment is stopped
    }


}