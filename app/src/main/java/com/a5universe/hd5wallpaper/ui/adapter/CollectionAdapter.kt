package com.a5universe.hd5wallpaper.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.a5universe.hd5wallpaper.R
import com.bumptech.glide.Glide

class CollectionAdapter(private val requireContext: Context, private val listBestOfMonth: ArrayList<String>)
    :RecyclerView.Adapter<CollectionAdapter.bomViewHolder>() {

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
        Glide.with(requireContext).load(listBestOfMonth[position]).into(holder.imageView)

    }

    override fun getItemCount() =listBestOfMonth.size
    
}
