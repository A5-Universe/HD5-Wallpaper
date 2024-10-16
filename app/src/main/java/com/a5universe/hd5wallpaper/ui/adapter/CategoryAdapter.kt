package com.a5universe.hd5wallpaper.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.a5universe.hd5wallpaper.ui.activities.category.CategoryActivity
import com.a5universe.hd5wallpaper.R
import com.a5universe.hd5wallpaper.data.CategoryModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CategoryAdapter(
    private val requireContext: Context,
    private val listOfCategory: List<CategoryModel>
) : RecyclerView.Adapter<CategoryAdapter.bomViewHolder>() {


    private val  filteredListOfCategory = mutableListOf<CategoryModel>()
    private var query: String = ""
    init {
        filteredListOfCategory.addAll(listOfCategory)
    }


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
        Glide.with(requireContext)
            .load(listOfCategory[position].link)
            .into(holder.imageView)

        // Set visibility based on query match
        holder.itemView.visibility =
            if (query.isEmpty() || listOfCategory[position].name.toLowerCase().contains(query.toLowerCase())) {
                View.VISIBLE
            } else {
                View.GONE
            }


        // Add this line to set the visibility of items
        holder.itemView.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            if (holder.itemView.visibility == View.VISIBLE) {
                RecyclerView.LayoutParams.WRAP_CONTENT
            } else {
                0
            }
        )

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, CategoryActivity::class.java)
            intent.putExtra("uid",listOfCategory[position].id)
            intent.putExtra("name",listOfCategory[position].name)
            requireContext.startActivity(intent)
        }

    }

    override fun getItemCount() =listOfCategory.size

    fun filter(newQuery: String) {
        query = newQuery
        notifyDataSetChanged()
    }

}