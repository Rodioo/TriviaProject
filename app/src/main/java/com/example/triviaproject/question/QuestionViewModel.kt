package com.example.triviaproject.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.triviaproject.categories.Category
import com.example.triviaproject.repository.TriviaApi
import com.example.triviaproject.utils.Difficulties
import com.example.triviaproject.utils.Status
import kotlinx.coroutines.*

//TODO: Add progress bar when loading by making an observer on status
class QuestionViewModel(val category: Category): ViewModel() {

    val NUMBER_OF_QUESTIONS = 9
    private val NUMBER_OF_QUESTIONS_PER_DIFFICULTY = NUMBER_OF_QUESTIONS / 3

    private val _currentQuestionNumber = MutableLiveData<Int>()
    val currentQuestionNumber: LiveData<Int>
        get() = _currentQuestionNumber

    private val _currentQuestionText = MutableLiveData<String>()
    val currentQuestionText: LiveData<String>
        get() = _currentQuestionText

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question>
        get() = _currentQuestion

    private val _questions = MutableLiveData<List<Question>>()

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private var viewModelJob: Job = Job()
    private var coroutineScope: CoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        retryRequest()
    }

    fun retryRequest() {
        _status.value = Status.ONGOING

        getQuestions(Difficulties.EASY.value)
        getQuestions(Difficulties.MEDIUM.value)
        getQuestions(Difficulties.HARD.value)
    }

    fun onCorrect() {
        _currentQuestionNumber.value = _currentQuestionNumber.value?.plus(1)
        _currentQuestionText.value = "${_currentQuestionNumber.value?.plus(1)}/$NUMBER_OF_QUESTIONS"
        if (_currentQuestionNumber.value!! < NUMBER_OF_QUESTIONS) {
            _currentQuestion.value = _questions.value?.get(_currentQuestionNumber.value!!)
        }
    }

    private fun getQuestions(difficulty: String, limit: Int = NUMBER_OF_QUESTIONS_PER_DIFFICULTY, type: String = "text_choice") {
        coroutineScope.launch {
            val response = TriviaApi.retrofitMoshiService.getQuestions(
                joinedCategories = category.subcategories.joinToString(separator = ","),
                difficulty = difficulty,
                limit = limit,
                type = type
            )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _status.value = Status.DONE_SUCCESS
                    response.body()!!.forEach {
                        _questions.value = _questions.value?.plus(it) ?: listOf(it)
                    }
                    _currentQuestion.value = _questions.value?.get(0)
                } else {
                    _status.value = Status.DONE_FAILURE
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}