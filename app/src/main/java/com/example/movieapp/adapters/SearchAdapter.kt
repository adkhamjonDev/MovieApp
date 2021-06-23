package com.example.movieapp.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ImagesItemBinding
import com.example.movieapp.databinding.LinearItemBinding
import com.example.movieapp.databinding.SimilarItemBinding
import com.example.movieapp.models.Images.Backdrop
import com.example.movieapp.models.MovieClass
import com.example.movieapp.models.Similar.Results
import com.squareup.picasso.Picasso
class SearchAdapter(var list: List<MovieClass>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    inner class MyViewHolder(var linearItemBinding: LinearItemBinding): RecyclerView.ViewHolder(
        linearItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LinearItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.linearItemBinding.tittle.text=obj.original_title
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${obj.poster_path}")
            .into(holder.linearItemBinding.icon)
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