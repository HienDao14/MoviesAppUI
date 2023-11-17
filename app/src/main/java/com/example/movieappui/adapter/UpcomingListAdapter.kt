package com.example.movieappui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieappui.ui.activity.FilmDetailActivity
import com.example.movieappui.databinding.UpcomingItemBinding
import com.example.movieappui.domain.Data

class UpcomingListAdapter() : RecyclerView.Adapter<UpcomingListAdapter.FilmListViewHolder>() {
    private lateinit var context: Context
    private var list = ArrayList<Data>()

    fun setListFilm(listFilm : List<Data>){
        this.list = listFilm as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class FilmListViewHolder(binding : UpcomingItemBinding) : RecyclerView.ViewHolder(binding.root){
        val filmThumb = binding.ivFilmThumb
        val filmTitle = binding.tvFilmTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListViewHolder {
        context = parent.context
        return FilmListViewHolder(UpcomingItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        val film = list[position]
        holder.filmTitle.text = film.title
        val requestOptions = RequestOptions().transform(FitCenter(), RoundedCorners(30))
        Glide.with(holder.itemView).load(film.poster).apply(requestOptions).into(holder.filmThumb)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, FilmDetailActivity::class.java)
            intent.putExtra("id", film.id)
            context.startActivity(intent)
        }
    }
}