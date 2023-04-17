package com.example.triviaproject.question

data class Question(
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val question: QuestionTitle,
    @Transient var answers: List<String> = emptyList()
) {
    init {
        answers = mutableListOf<String>().apply {
            add(correctAnswer)
            addAll(incorrectAnswers)
        }.shuffled()
    }
}

data class QuestionTitle(
    val text: String
)
