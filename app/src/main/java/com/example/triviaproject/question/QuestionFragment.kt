package com.example.triviaproject.question

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.triviaproject.R
import com.example.triviaproject.databinding.FragmentQuestionBinding
import kotlin.math.log

class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var viewModel: QuestionViewModel
    private lateinit var viewModelFactory: QuestionViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionBinding.inflate(inflater)

        viewModelFactory = QuestionViewModelFactory(QuestionFragmentArgs.fromBundle(requireArguments()).selectedCategory)
        viewModel = ViewModelProvider(this, viewModelFactory)[QuestionViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initBindings()

        return binding.root
    }

    private fun onSubmitAnswer(view: View) {
        val answerButton = view as Button
        binding.hasSubmittedAnswer = true

        viewModel.currentQuestion.observe(viewLifecycleOwner) { currentQuestion ->
            if (answerButton.text.toString() == currentQuestion.correctAnswer) {
                updateUIAnswerCorrect()
            } else {
                updateUIAnswerIncorrect()
            }
        }

        binding.feedbackText.visibility = View.VISIBLE
        binding.feedbackButton.visibility = View.VISIBLE
    }

    private fun updateUIAnswerIncorrect() {
        binding.feedbackText.text = resources.getString(R.string.incorrect_answrer)
        binding.feedbackButton.text = resources.getString(R.string.try_again)
    }

    private fun updateUIAnswerCorrect() {
        binding.feedbackText.text = resources.getString(R.string.correct_answer)
        binding.feedbackButton.text = resources.getString(R.string.next_question)
    }

    private fun initBindings() {
        binding.hasSubmittedAnswer = false

        binding.answer1.setOnClickListener { view -> onSubmitAnswer(view) }
        binding.answer2.setOnClickListener { view -> onSubmitAnswer(view) }
        binding.answer3.setOnClickListener { view -> onSubmitAnswer(view) }
        binding.answer4.setOnClickListener { view -> onSubmitAnswer(view) }
    }
}