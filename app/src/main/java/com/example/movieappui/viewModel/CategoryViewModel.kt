package com.example.movieappui.viewModel

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieappui.domain.Category
import com.example.movieappui.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class CategoryViewModel: ViewModel() {

    private val _categoryLiveData = MutableLiveData<Category>()
    val categoryLiveData : LiveData<Category>
        get() = _categoryLiveData

    private lateinit var loading : ProgressBar

    fun setLoadingProgressBar(loading : ProgressBar){
        this.loading = loading
    }

    fun getCategory(){
        RetrofitInstance.retrofitService.getCategories().enqueue(object : Callback<Category> {
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                loading.visibility = View.GONE
                if(response.body() != null){
                    _categoryLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                loading.visibility = View.GONE
                Log.e("CategoryViewModel", t.message.toString())
            }
        })
    }
}