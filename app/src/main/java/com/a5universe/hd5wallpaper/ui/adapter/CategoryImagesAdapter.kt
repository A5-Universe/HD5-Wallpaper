package com.a5universe.hd5wallpaper.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.a5universe.hd5wallpaper.ui.activities.set_wallpaper.FinalWallpaper
import com.a5universe.hd5wallpaper.R
import com.a5universe.hd5wallpaper.data.BestOfMonthModel
import com.bumptech.glide.Glide

class CategoryImagesAdapter(private val requireContext: Context, private val listBestOfMonth: ArrayList<BestOfMonthModel>)
    :RecyclerView.Adapter<CategoryImagesAdapter.BomViewHolder>() {

    inner class BomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView = itemView.findViewById<ImageView>(R.id.catImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BomViewHolder {
       return BomViewHolder(
           LayoutInflater.from(requireContext).inflate(R.layout.item_wallpaper,parent,false)
       )
    }

    override fun onBindViewHolder(holder: BomViewHolder, position: Int) {
        val imageUrlOrPath = listBestOfMonth[position].link

        Glide.with(requireContext)
            .load(listBestOfMonth[position].link)
            .into(holder.imageView)

        // Set item click listener
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, FinalWallpaper::class.java)
            val imageType: String

            imageType = if (isValidUrl(imageUrlOrPath)) { "link" } else { "path" }

            // Set the extras for the intent
            intent.putExtra("imageType", imageType)
            intent.putExtra("link", imageUrlOrPath)

            requireContext.startActivity(intent)
        }

    }

    // Validate url or not
    private fun isValidUrl(url: String): Boolean {
        return try {
            val uri = Uri.parse(url)
            uri.scheme?.startsWith("http") == true
        } catch (e: Exception) {
            false
        }
    }

    override fun getItemCount() =listBestOfMonth.size


}
