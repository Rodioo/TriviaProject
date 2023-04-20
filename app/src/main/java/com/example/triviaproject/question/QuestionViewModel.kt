package com.example.triviaproject.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.triviaproject.categories.Category
import com.example.triviaproject.repository.TriviaApi
import com.example.triviaproject.utils.Difficulties
import kotlinx.coroutines.*

//TODO: Add progress bar when loading by making an observer on status
class QuestionViewModel(val category: Category): ViewModel() {

    private val _currentQuestionNumber = MutableLiveData<Int>()

    private val _currentQuestionText = MutableLiveData<String>()
    val currentQuestionText: LiveData<String>
        get() = _currentQuestionText

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question>
        get() = _currentQuestion

    private val _questions = MutableLiveData<List<Question>>()

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private var viewModelJob: Job = Job()
    private var coroutineScope: CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getQuestions(Difficulties.EASY.value)
        getQuestions(Difficulties.MEDIUM.value)
        getQuestions(Difficulties.HARD.value)

        _currentQuestionNumber.value = 0
        _currentQuestionText.value = "1/9"
    }

    fun onCorrect() {
        _currentQuestionNumber.value = _currentQuestionNumber.value?.plus(1)
        _currentQuestionText.value = "${_currentQuestionNumber.value?.plus(1)}/9"
        _currentQuestion.value = _questions.value?.get(_currentQuestionNumber.value!!)
    }

    private fun getQuestions(difficulty: String, limit: Int = 3, type: String = "text_choice") {
        coroutineScope.launch {
            val response = TriviaApi.retrofitMoshiService.getQuestions(
                joinedCategories = category.subcategories.joinToString(separator = ","),
                difficulty = difficulty,
                limit = limit,
                type = type
            )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = "SUCCESS"
                    response.body()!!.forEach {
                        _questions.value = _questions.value?.plus(it) ?: listOf(it)
                    }
                    _currentQuestion.value = _questions.value?.get(0)
                } else {
                    _status.value = "Failure: ${response.message()}"
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}