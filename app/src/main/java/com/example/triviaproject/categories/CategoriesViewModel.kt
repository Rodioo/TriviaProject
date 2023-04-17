package com.example.triviaproject.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.triviaproject.repository.TriviaApi
import com.google.firebase.inject.Deferred
import kotlinx.coroutines.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel: ViewModel() {

    private val allCategories = MutableLiveData<List<Category>>()
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    var selectedCategory = MutableLiveData<Category>()

    private var viewModelJob: Job = Job()
    private var coroutineScope: CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getCategories()
    }

    private fun getCategories() {
        coroutineScope.launch {
            val response = TriviaApi.retrofitScalarsService.getCategories()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = "SUCCESS"

                    val parsedResponse = CategoriesUtils.parseResponse(response.body()!!)
                    _categories.value = parsedResponse
                    allCategories.value = parsedResponse
                } else {
                    _status.value = "Failure: ${response.message()}"
                }
            }
        }
    }

    fun filterCategories(query: String?) {
        if (query.isNullOrEmpty()) {
            _categories.value = allCategories.value
        } else {
            val filteredCategories = categories.value?.filter {
                category -> category.name.contains(query, ignoreCase = true)
            }
            _categories.value = filteredCategories!!
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}