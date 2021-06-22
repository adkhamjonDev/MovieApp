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
class SearchAdapter(var list: ArrayList<MovieClass>, var onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>(), Filterable {
    val list1=ArrayList<MovieClass>(list)
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
        holder.linearItemBinding.tittle.text=obj.title
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
    override fun getFilter(): Filter {
        return exampleFilter
    }
    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val newList = ArrayList<MovieClass>()
            if (constraint == null || constraint.isEmpty()) {
                newList.addAll(list1)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (i in 0 until list1.size) {
                    if (list1[i].title.toLowerCase()!!.contains(filterPattern)) {
                        newList.add(list1[i])
                    }
                }
            }
            val results = FilterResults()
            results.values = newList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            list.clear()
            list.addAll(results.values as ArrayList<MovieClass>)
            notifyDataSetChanged()
        }
    }
}