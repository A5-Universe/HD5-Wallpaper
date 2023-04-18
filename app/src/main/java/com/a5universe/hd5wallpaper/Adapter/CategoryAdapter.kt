package com.a5universe.hd5wallpaper.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a5universe.hd5wallpaper.CategoryActivity
import com.a5universe.hd5wallpaper.Model.categoryModel
import com.a5universe.hd5wallpaper.R
import com.bumptech.glide.Glide

class CategoryAdapter(
    val requireContext: Context,
    val listOfCategory: ArrayList<categoryModel>) : RecyclerView.Adapter<CategoryAdapter.bomViewHolder>() {


    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val imageView = itemView.findViewById<ImageView>(R.id.cat_img)
        val name = itemView.findViewById<TextView>(R.id.cat_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
       return bomViewHolder(
           LayoutInflater.from(requireContext).inflate(R.layout.item_category,parent,false)
       )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
       holder.name.text = listOfCategory[position].name
        Glide.with(requireContext).load(listOfCategory[position].link).into(holder.imageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext,CategoryActivity::class.java)
            intent.putExtra("uid",listOfCategory[position].id)
            intent.putExtra("name",listOfCategory[position].name)
            requireContext.startActivity(intent)
        }

    }

    override fun getItemCount() =listOfCategory.size


}
