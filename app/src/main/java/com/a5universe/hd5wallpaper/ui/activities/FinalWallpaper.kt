package com.a5universe.hd5wallpaper.ui.activities

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.core.view.WindowCompat
import com.a5universe.hd5wallpaper.databinding.ActivityFinalWallpaperBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.util.*

class FinalWallpaper : AppCompatActivity() {

    private lateinit var binding: ActivityFinalWallpaperBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // for full screen status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        val url = intent.getStringExtra("link")
        val urlImage = URL(url)


        binding.finalWallpaper
        Glide.with(this).load(url).into(binding.finalWallpaper)

        // set wallpaper button click event
        binding.btnSetWallpaper.setOnClickListener{
            //set wallpaper
            val result: kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {//test.. kotlinx.coroutines...
                urlImage.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main){
                val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                wallpaperManager.setBitmap(result.await())
            }

        }

        // download button click event
        binding.btnDownload.setOnClickListener {
            // download wallpaper
            val result: kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {//test.. kotlinx.coroutines...
                urlImage.toBitmap()
            }
            GlobalScope.launch(Dispatchers.Main){
                saveImage(result.await())
            }

        }
    }



    private fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        }catch (e: IOException){
            null
        }
    }

    private fun saveImage(image: Bitmap?){
        val random1 = Random().nextInt(520965)
        val random2 = Random().nextInt(952463)

        val name = "HD5-${random1+random2}"

        val data: OutputStream
        try {
            val resolver = contentResolver
            val contentValues = contentValuesOf()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"$name.jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
            Environment.DIRECTORY_PICTURES + File.separator + "HD5 Wallpaper")
            val  imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
            data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
            image?.compress(Bitmap.CompressFormat.JPEG,100,data)
            Objects.requireNonNull<OutputStream>(data)
            Toast.makeText(this, "Wallpaper Save", Toast.LENGTH_SHORT).show()

        } catch (e: Exception){
            Toast.makeText(this, "Wallpaper Not Save", Toast.LENGTH_SHORT).show()
        }

    }

}