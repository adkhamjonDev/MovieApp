package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.CompanyItemBinding
import com.example.movieapp.models.Details.ProductionCompany
import com.squareup.picasso.Picasso

class CompanyItemAdapter(var list: List<ProductionCompany>):
    RecyclerView.Adapter<CompanyItemAdapter.MyViewHolder>(){
    inner class MyViewHolder(var companyItemBinding: CompanyItemBinding): RecyclerView.ViewHolder(
        companyItemBinding.root){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CompanyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj=list[position]
        holder.companyItemBinding.name.text=obj.name
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${obj.logo_path}").placeholder(R.drawable.place)
            .into(holder.companyItemBinding.icon)
    }
    override fun getItemCount(): Int {
        return list.size
    }
}