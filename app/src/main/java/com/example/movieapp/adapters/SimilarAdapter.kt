package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ImagesItemBinding
import com.example.movieapp.databinding.SimilarItemBinding
import com.example.movieapp.models.Images.Backdrop
import com.example.movieapp.models.Similar.Results
import com.squareup.picasso.Picasso

class SimilarAdapter(var list: List<Results>,var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<SimilarAdapter.MyViewHolder>(){
    inner class MyViewHolder(var similarItemBinding: SimilarItemBinding): RecyclerView.ViewHolder(
        similarItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SimilarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.similarItemBinding.name.text=obj.title
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${obj.poster_path}")
            .into(holder.similarItemBinding.icon)
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(obj.id)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickListener{
        fun onItemClick(id:Int)
    }
}