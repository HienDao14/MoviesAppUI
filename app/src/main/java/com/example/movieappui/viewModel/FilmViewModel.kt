package com.example.movieappui.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappui.domain.Category
import com.example.movieappui.domain.CategoryItem
import com.example.movieappui.domain.Data
import com.example.movieappui.domain.ListFilm
import com.example.movieappui.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmViewModel: ViewModel() {
    private val _moviesLiveData = MutableLiveData<List<Data>>()
    val moviesLiveData : LiveData<List<Data>>
        get() = _moviesLiveData

    private val _upcomingLiveData = MutableLiveData<List<Data>>()
    val upcomingLiveData : LiveData<List<Data>>
        get() = _upcomingLiveData

    private val _categoriesLiveData = MutableLiveData<List<CategoryItem>>()
    val categoriesLiveData : LiveData<List<CategoryItem>>
        get() = _categoriesLiveData

    @SuppressLint("StaticFieldLeak")
    private lateinit var loadingPopular : ProgressBar
    private lateinit var loadingUpcoming: ProgressBar


    fun setLoadingPopular(loading : ProgressBar){
        loadingPopular = loading
    }

    fun setLoadingUpcoming(loading : ProgressBar){
        loadingUpcoming = loading
    }

    fun getMovies(page : Int){
        RetrofitInstance.retrofitService.getMoviesList(page).enqueue(object : Callback<ListFilm>{
            override fun onResponse(call: Call<ListFilm>, response: Response<ListFilm>) {
                loadingPopular.visibility = View.GONE
                if(response.body() != null){
                    _moviesLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<ListFilm>, t: Throwable) {
                loadingPopular.visibility = View.GONE
                Log.e("Board Fragment", t.message.toString())
            }
        })
    }

    fun getUpcomingMovies(page: Int){
        RetrofitInstance.retrofitService.getMoviesList(page).enqueue(object: Callback<ListFilm>{
            override fun onResponse(call: Call<ListFilm>, response: Response<ListFilm>) {
                loadingUpcoming.visibility = View.GONE
                if(response.body() != null){
                    _upcomingLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<ListFilm>, t: Throwable) {
                loadingUpcoming.visibility = View.GONE
                Log.e("Board Fragment", t.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.retrofitService.getCategories().enqueue(object: Callback<Category>{
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                loadingUpcoming.visibility = View.GONE
                if(response.body() != null){
                    _categoriesLiveData.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                loadingUpcoming.visibility = View.GONE
                Log.e("Board Fragment", t.message.toString())
            }
        })
    }
}