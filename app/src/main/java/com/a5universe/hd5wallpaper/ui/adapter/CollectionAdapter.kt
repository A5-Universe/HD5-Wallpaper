package com.a5universe.hd5wallpaper.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.a5universe.hd5wallpaper.R
import com.a5universe.hd5wallpaper.ui.activities.set_wallpaper.FinalWallpaper
import com.bumptech.glide.Glide

class CollectionAdapter(
    private val requireContext: Context,
    private val listBestOfMonth: ArrayList<String>
) : RecyclerView.Adapter<CollectionAdapter.BomViewHolder>() {

    inner class BomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.catImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BomViewHolder {

        return BomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_wallpaper, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BomViewHolder, position: Int) {
        Glide.with(requireContext)
            .load(listBestOfMonth[position])
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, FinalWallpaper::class.java)
            val link = listBestOfMonth[position]
            val imageType: String

            // Determine if the link is valid
            if (isValidUrl(link)) {
                imageType = "link"
            } else {
                imageType = "path"
            }

            // Set the extras for the intent
            intent.putExtra("imageType", imageType)
            intent.putExtra("link", link)

            // Start the activity
            requireContext.startActivity(intent)
        }

    }

    //check url valid or not
    private fun isValidUrl(url: String): Boolean {
        return try {
            val uri = Uri.parse(url)
            uri.scheme?.startsWith("http") == true
        } catch (e: Exception) {
            false
        }
    }

    override fun getItemCount() = listBestOfMonth.size

}
