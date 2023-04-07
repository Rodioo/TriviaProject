package com.example.triviaproject.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.triviaproject.R
import com.example.triviaproject.databinding.FragmentCategoriesBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class CategoriesFragment : Fragment() {

    private lateinit var adapter: CategoriesAdapter
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)

        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = CategoriesAdapter()
        binding.categoriesList.adapter = adapter

        binding.signOutButton.setOnClickListener {
            signOut()
        }

        return binding.root
    }

    private fun signOut() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        googleSignInClient.signOut()

        view?.findNavController()?.navigate(R.id.action_categoriesFragment_to_loginFragment)
    }
}