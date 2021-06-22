package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ImagesItemBinding
import com.example.movieapp.models.Images.Backdrop
import com.squareup.picasso.Picasso

class ImageItemAdapter(var list: List<Backdrop>):
    RecyclerView.Adapter<ImageItemAdapter.MyViewHolder>(){
    inner class MyViewHolder(var imagesItemBinding: ImagesItemBinding): RecyclerView.ViewHolder(
        imagesItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ImagesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${obj.file_path}")
            .into(holder.imagesItemBinding.icon)
    }
    override fun getItemCount(): Int {
        return list.size
    }
}