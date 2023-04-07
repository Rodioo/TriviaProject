package com.example.triviaproject.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.triviaproject.repository.TriviaApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel: ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private var selectedCategory = null

    init {
        getCategories()
    }

    private fun getCategories() {
        TriviaApi.retrofitService.getCategories().enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure ${t.message}"
            }

        })
        _response.value = ""
    }
}