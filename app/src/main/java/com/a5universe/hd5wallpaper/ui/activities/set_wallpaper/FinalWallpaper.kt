package com.a5universe.hd5wallpaper.ui.activities.set_wallpaper

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.core.view.WindowCompat
import com.a5universe.hd5wallpaper.databinding.ActivityFinalWallpaperBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.util.*

class FinalWallpaper : AppCompatActivity() {

    private lateinit var binding: ActivityFinalWallpaperBinding
    private var isDownloaded = false
    private var downloadedImagePath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up full-screen status bar
        setupFullscreen()


        // Retrieve link or path from the intent
        val imageType = intent.getStringExtra("imageType")
        val linkOrPath = intent.getStringExtra("link")

        // Log received values
        Log.d("TAG", "onCreate: imageType = $imageType")
        Log.d("TAG", "onCreate: linkOrPath = $linkOrPath")

        // Handle null values
        if (linkOrPath == null) {
            Log.d("TAG", "Link or path is null")
            finish() // Close the activity if link or path is null
            return
        }

        // Handle image loading based on type
        when (imageType) {
            "link" -> handleImageFromUrl(linkOrPath)
            "path" -> handleImageFromPath(linkOrPath)
            else -> {
                Log.d("TAG", "Invalid image type")
                Toast.makeText(this, "Invalid image type", Toast.LENGTH_SHORT).show()
                finish() // Close the activity if the image type is invalid
            }
        }


    }

    //setup full screen
    private fun setupFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }


    //handle from image == url
    private fun handleImageFromUrl(urlString: String?) {
        if (urlString == null || !isValidUrl(urlString)) {
            Log.e("FinalWallpaper", "Invalid URL: $urlString")
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
            return
        }

        val url = URL(urlString)
        Glide.with(this).load(url).into(binding.finalWallpaper)

        // Show download and set wallpaper buttons
        binding.btnDownload.visibility = View.VISIBLE
        binding.btnSetWallpaper.visibility = View.VISIBLE

        binding.btnSetWallpaper.setOnClickListener {
            if (isDownloaded && downloadedImagePath != null) {
                setWallpaperFromPath(downloadedImagePath!!)
            } else {
                setWallpaperFromUrl(url)
            }
        }

        binding.btnDownload.setOnClickListener {
            val urlImage = URL(urlString)

            val result: kotlinx.coroutines.Deferred<Bitmap?> =
                GlobalScope.async {//test.. kotlinx.coroutines...
                    urlImage.toBitmap()
                }
            GlobalScope.launch(Dispatchers.Main) {
                saveImage(result.await())
            }
        }
    }

    //handle from image == path
    private fun handleImageFromPath(pathString: String?) {
        downloadedImagePath = pathString
        binding.finalWallpaper.setImageBitmap(BitmapFactory.decodeFile(downloadedImagePath))

        // Only show the set wallpaper button for local paths
        binding.btnDownload.visibility = View.GONE
        binding.btnSetWallpaper.visibility = View.VISIBLE


        binding.btnSetWallpaper.setOnClickListener {
            setWallpaperFromPath(downloadedImagePath!!)
        }
    }

    //set wallpaper if url
    private fun setWallpaperFromUrl(url: URL) {
        //set wallpaper
        val result: kotlinx.coroutines.Deferred<Bitmap?> =
            GlobalScope.async {//test.. kotlinx.coroutines...
                url.toBitmap()
            }

        GlobalScope.launch(Dispatchers.Main) {
            val wallpaperManager = WallpaperManager.getInstance(applicationContext)
            wallpaperManager.setBitmap(result.await())
        }
    }

    //set wallpaper if path
    private fun setWallpaperFromPath(path: String) {
        val bitmap = BitmapFactory.decodeFile(path)
        if (bitmap != null) {
            val wallpaperManager = WallpaperManager.getInstance(applicationContext)
            try {
                wallpaperManager.setBitmap(bitmap)
                Toast.makeText(this, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this, "Failed to set wallpaper: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }


    //check url is valid or not
    fun isValidUrl(url: String): Boolean {
        return try {
            val uri = Uri.parse(url)
            uri.scheme?.startsWith("http") == true
        } catch (e: Exception) {
            false
        }
    }

    //convert url to bitmap
    private fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException) {
            null
        }
    }

    //save image from storage
    private fun saveImage(image: Bitmap?) {
        val random1 = Random().nextInt(520965)
        val random2 = Random().nextInt(952463)

        val name = "HD5-${random1 + random2}"

        val data: OutputStream
        try {
            val resolver = contentResolver
            val contentValues = contentValuesOf()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "HD5 Wallpaper"
            )
            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
            image?.compress(Bitmap.CompressFormat.JPEG, 100, data)
            Objects.requireNonNull<OutputStream>(data)
            Toast.makeText(this, "Wallpaper Save", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Wallpaper Not Save", Toast.LENGTH_SHORT).show()
        }

    }


}