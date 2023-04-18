package com.a5universe.hd5wallpaper.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a5universe.hd5wallpaper.FinalWallpaper
import com.a5universe.hd5wallpaper.Model.BomModel
import com.a5universe.hd5wallpaper.R
import com.bumptech.glide.Glide

class CatImagesAdapter(val requireContext: Context, val listBestOfMonth: ArrayList<BomModel>)
    :RecyclerView.Adapter<CatImagesAdapter.bomViewHolder>() {

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView = itemView.findViewById<ImageView>(R.id.catImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
       return bomViewHolder(
           LayoutInflater.from(requireContext).inflate(R.layout.item_wallpaper,parent,false)
       )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        Glide.with(requireContext).load(listBestOfMonth[position].link).into(holder.imageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext,FinalWallpaper::class.java)
            intent.putExtra("link",listBestOfMonth[position].link)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount() =listBestOfMonth.size


}
