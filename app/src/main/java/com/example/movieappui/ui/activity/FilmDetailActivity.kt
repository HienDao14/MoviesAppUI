package com.example.movieappui.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieappui.R
import com.example.movieappui.adapter.CategoriesAdapter
import com.example.movieappui.adapter.GenresAdapter
import com.example.movieappui.databinding.ActivityFilmDetailBinding
import com.example.movieappui.viewModel.DetailViewModel
import com.example.movieappui.viewModel.FilmViewModel

class FilmDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFilmDetailBinding
    private lateinit var genresAdapter: GenresAdapter
    private val viewModel : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idMovie = intent.getIntExtra("id", 1);
        Log.d("DetailActivity", idMovie.toString())
        setUpGenresAdapter()
        val loading = binding.detailProgressBar
        loading.visibility = View.VISIBLE
        viewModel.setUpLoading(loading)
        viewModel.getMovie(idMovie)
        getMovieDetail()

        onBackClick()
    }

    private fun onBackClick() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpGenresAdapter() {
        genresAdapter = GenresAdapter()
        binding.rvCategories.apply {
            layoutManager =LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = genresAdapter
        }
    }

    private fun getMovieDetail() {
        viewModel.movieLiveData.observe(this, Observer {
            Glide.with(this).load(it.poster).into(binding.imgFilm)
            binding.tvName.text = it.title
            binding.tvRating.text = it.imdb_rating
            binding.tvTime.text = it.runtime
            genresAdapter.setListGenres(it.genres)
            binding.tvDetail.text = it.plot
            binding.tvActors.text = it.actors
        } )
    }
}