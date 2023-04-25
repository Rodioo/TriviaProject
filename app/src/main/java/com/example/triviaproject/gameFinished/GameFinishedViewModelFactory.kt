package com.example.triviaproject.gameFinished

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.triviaproject.categories.Category

class GameFinishedViewModelFactory(private val category: Category, private val numOfCorrectAnswers: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameFinishedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameFinishedViewModel(category, numOfCorrectAnswers) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
