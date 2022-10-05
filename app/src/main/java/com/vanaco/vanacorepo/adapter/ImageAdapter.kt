package com.vanaco.vanacorepo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanaco.vanacorepo.R
import com.vanaco.vanacorepo.model.UserImage
import com.vanaco.vanacorepo.view.FullWallpaper

class ImageAdapter(
    private val imagesList: ArrayList<UserImage>,
    private val context: Context
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context).load(imagesList[position].userImage).into(holder.image)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullWallpaper::class.java)
            intent.putExtra("userImage", imagesList[position].userImage)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }
}
