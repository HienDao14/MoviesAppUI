package com.example.movieappui.network

import com.example.movieappui.domain.Category
import com.example.movieappui.domain.Data
import com.example.movieappui.domain.ListFilm
import com.example.movieappui.domain.Movie
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://moviesapi.ir/api/v1/"

interface FilmApiService{
    @GET("movies")
    fun getMoviesList(@Query("page") page : Int) : Call<ListFilm>

    @GET("movies/{idFilm}")
    fun getMovieDetail(@Path("idFilm") idFilm : Int) : Call<Movie>

    @GET("genres")
    fun getCategories(): Call<Category>
}



object RetrofitInstance {

    val retrofitService : FilmApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(FilmApiService::class.java)
    }
}