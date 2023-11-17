package com.example.movieappui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.movieappui.R
import com.example.movieappui.domain.SliderItem


class SliderAdapter(private val list: ArrayList<SliderItem>,private val viewPager2: ViewPager2)
    : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image_slider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderAdapter.SliderViewHolder {
//        view.setLayoutParams(
//            ConstraintLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//        )
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.slide_item, parent, false)
        return SliderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val currentItem = list[position]
        Glide.with(holder.itemView).load(currentItem.image).into(holder.image)
        if(position == list.size - 1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private val runnable = Runnable {
        list.addAll(list)
        notifyDataSetChanged()
    }
}