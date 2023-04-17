package com.example.triviaproject.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.triviaproject.categories.Category

class QuestionViewModelFactory(private val category: Category): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel(category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}