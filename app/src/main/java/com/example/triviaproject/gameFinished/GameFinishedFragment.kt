package com.example.triviaproject.gameFinished

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.triviaproject.R
import com.example.triviaproject.databinding.FragmentGameFinishedBinding
import com.example.triviaproject.databinding.FragmentQuestionBinding
import com.example.triviaproject.question.QuestionFragmentArgs
import com.example.triviaproject.question.QuestionViewModel
import com.example.triviaproject.question.QuestionViewModelFactory

class GameFinishedFragment : Fragment() {

    private lateinit var binding: FragmentGameFinishedBinding
    private lateinit var viewModel: GameFinishedViewModel
    private lateinit var viewModelFactory: GameFinishedViewModelFactory
    private lateinit var args: GameFinishedFragmentArgs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameFinishedBinding.inflate(inflater)

        args = GameFinishedFragmentArgs.fromBundle(requireArguments())

        viewModelFactory = GameFinishedViewModelFactory(
            args.selectedCategory,
            args.numOfCorrectAnswers
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[GameFinishedViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if (getShareIntent().resolveActivity(requireActivity().packageManager) == null) {
            binding.shareButton.visibility = View.INVISIBLE
        }

        binding.shareButton.setOnClickListener {
            shareSuccess()
        }

        binding.playAgainButton.setOnClickListener {
            goToCategoriesFragment()
        }

        return binding.root
    }

    private fun getShareIntent(): Intent {
        return ShareCompat.IntentBuilder(requireActivity())
            .setText("I've just finished a quiz based on ${args.selectedCategory.name} where I correctly answered ${args.numOfCorrectAnswers} questions")
            .setType("text/plain")
            .intent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    private fun goToCategoriesFragment() {
        view?.findNavController()?.navigate(GameFinishedFragmentDirections.actionGameFinishedFragmentToCategoriesFragment())
    }
}