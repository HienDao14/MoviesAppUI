package com.example.movieappui.network

import com.example.movieappui.domain.Movie
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val BASE_URL2 = "https://moviesdatabase.p.rapidapi.com/titles"

interface MoviesApiService{
    @Headers("X-RapidAPI-Key : f5cf038b0cmshc38b98f86a9ca5cp11b826jsn807a2ed35995", "X-RapidAPI-Host: moviesdatabase.p.rapidapi.com")
    @GET("series/{seriesId}")
    fun getSeries(@Path("seriesId") seriesId: Int) : Call<Movie>
}

val client = OkHttpClient()

val request = Request.Builder()
    .url("/series/%7BseriesId%7D")
    .get()
    .addHeader("X-RapidAPI-Key", "f5cf038b0cmshc38b98f86a9ca5cp11b826jsn807a2ed35995")
    .addHeader("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
    .build()

val response = client.newCall(request).execute()
