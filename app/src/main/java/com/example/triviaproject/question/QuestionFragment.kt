package com.example.triviaproject.question

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.triviaproject.R
import com.example.triviaproject.databinding.FragmentQuestionBinding

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


        initUI()

        return binding.root
    }

    private fun onSubmitAnswer(view: View) {
        val answerButton = view as Button

        viewModel.currentQuestion.observe(viewLifecycleOwner) { currentQuestion ->
            binding.hasAnsweredCorrectly = (answerButton.text.toString() == currentQuestion.correctAnswer)
        }
    }

    private fun onNextQuestion() {
        viewModel.onCorrect()
        binding.hasAnsweredCorrectly = null
    }

    private fun onTryAgain() {
        binding.hasAnsweredCorrectly = null
    }

    private fun initUI() {
        binding.hasAnsweredCorrectly = null

        binding.answer1.setOnClickListener { view -> onSubmitAnswer(view) }
        binding.answer2.setOnClickListener { view -> onSubmitAnswer(view) }
        binding.answer3.setOnClickListener { view -> onSubmitAnswer(view) }
        binding.answer4.setOnClickListener { view -> onSubmitAnswer(view) }
        binding.feedbackButton.setOnClickListener {
            if (binding.hasAnsweredCorrectly == true) {
                onNextQuestion()
            } else if (binding.hasAnsweredCorrectly == false){
                onTryAgain()
            }
        }
    }
}