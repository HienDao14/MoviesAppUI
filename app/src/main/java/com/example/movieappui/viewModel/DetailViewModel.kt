package com.example.movieappui.viewModel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappui.domain.Movie
import com.example.movieappui.network.FilmApiService
import com.example.movieappui.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _movieLiveData = MutableLiveData<Movie>()
    val movieLiveData : LiveData<Movie>
        get() = _movieLiveData

    private lateinit var loading : ProgressBar

    fun setUpLoading(loadingBar: ProgressBar){
        this.loading = loadingBar
    }

    fun getMovie(id : Int){
        RetrofitInstance.retrofitService.getMovieDetail(id).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.body() != null){
                    loading.visibility = View.GONE
                    _movieLiveData.value = response.body()
                }
                Log.e("DetailActivity", response.body()!!.title)
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                loading.visibility = View.GONE
                Log.e("DetailActivity", t.message.toString())
            }
        })
    }
}