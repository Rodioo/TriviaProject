package com.example.triviaproject.gameFinished

import androidx.lifecycle.ViewModel
import com.example.triviaproject.categories.Category

class GameFinishedViewModel(val category: Category, val numOfCorrectAnswers: Int): ViewModel() {
}