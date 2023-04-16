package com.example.triviaproject.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.triviaproject.R
import com.example.triviaproject.categories.Category
import com.example.triviaproject.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var selectedCategory: Category

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentQuestionBinding.inflate(inflater)

        selectedCategory = QuestionFragmentArgs.fromBundle(requireArguments()).selectedCategory

        Toast.makeText(this.context, selectedCategory.toString(), Toast.LENGTH_LONG).show()

        return binding.root
    }
}