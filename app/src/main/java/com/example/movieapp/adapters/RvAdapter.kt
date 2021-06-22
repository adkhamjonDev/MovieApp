package com.example.movieapp.adapters
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.GridImageBinding
import com.example.movieapp.databinding.LinearItemBinding
import com.example.movieapp.models.MovieClass
import com.squareup.picasso.Picasso

class RvAdapter(var gridLayoutManager: GridLayoutManager,var onItemClickListener: OnItemClickListener) :
    PagingDataAdapter<MovieClass, RecyclerView.ViewHolder>(MyDiffUtill()) {
    class MyDiffUtill : DiffUtil.ItemCallback<MovieClass>() {
        override fun areItemsTheSame(oldItem: MovieClass, newItem: MovieClass): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: MovieClass, newItem: MovieClass): Boolean {
            return oldItem == newItem
        }
    }
    inner class FromVh(var linearItemBinding: LinearItemBinding) :
        RecyclerView.ViewHolder(linearItemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(movieClass: MovieClass) {
            linearItemBinding.tittle.text=movieClass.title
            //linearItemBinding.date.text="Year: ${movieClass.release_date.substring(0,4)}"
            Picasso.get().load("https://image.tmdb.org/t/p/w500/${movieClass.poster_path}")
                .into(linearItemBinding.icon)
        }
    }
    inner class ToVh(var gridImageBinding: GridImageBinding) :
        RecyclerView.ViewHolder(gridImageBinding.root) {
        fun onBind(movieClass: MovieClass) {
            gridImageBinding.name.text=movieClass.title
            Picasso.get().load("https://image.tmdb.org/t/p/w500/${movieClass.poster_path}")
                .into(gridImageBinding.icon)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return FromVh(
                LinearItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return ToVh(GridImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            )
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            val fromVh = holder as FromVh
            getItem(position)?.let { movie->
                fromVh.onBind(movie)
                holder.itemView.setOnClickListener {
                    onItemClickListener.itemClick(movie)
                }
            }
        } else {
            val toVh = holder as ToVh
            getItem(position)?.let { movie->
                toVh.onBind(movie)
                holder.itemView.setOnClickListener {
                    onItemClickListener.itemClick(movie)
                }
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (gridLayoutManager.spanCount == 1) {
            return 1
        }
        return 2
    }
    interface OnItemClickListener{
         fun itemClick(movieClass: MovieClass)
    }
}