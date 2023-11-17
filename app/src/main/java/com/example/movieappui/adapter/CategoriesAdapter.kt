package com.example.movieappui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieappui.R
import com.example.movieappui.domain.CategoryItem

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private var list = ArrayList<CategoryItem>()

    fun setListCategory(listCategory : List<CategoryItem>){
        this.list = listCategory as ArrayList<CategoryItem>
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tvChip : TextView = view.findViewById(R.id.chip_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.tvChip.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }
}