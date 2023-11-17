package com.example.movieappui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappui.R
import com.example.movieappui.domain.Movie
import com.google.android.material.chip.Chip

class GenresAdapter : RecyclerView.Adapter<GenresAdapter.DetailViewHolder>() {
    private var list = ArrayList<String>()

    fun setListGenres(listGenres : List<String>){
        this.list = listGenres as ArrayList<String>
        notifyDataSetChanged()
    }

    inner class DetailViewHolder(view : View): RecyclerView.ViewHolder(view){
        val tvChip : Chip = view.findViewById(R.id.chip_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.category_item, parent, false
        )
        return DetailViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.tvChip.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}