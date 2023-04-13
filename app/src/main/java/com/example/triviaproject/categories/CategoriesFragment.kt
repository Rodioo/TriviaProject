package com.example.triviaproject.categories

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triviaproject.R
import com.example.triviaproject.databinding.FragmentCategoriesBinding
import com.example.triviaproject.utils.RecyclerViewSpacing
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var adapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoriesBinding.inflate(inflater)

        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val spacing = resources.getDimensionPixelSize(R.dimen.medium_margin)
        binding.categoriesRecycler.addItemDecoration(RecyclerViewSpacing(spacing))

        adapter = CategoriesAdapter()
        binding.categoriesRecycler.adapter = adapter

        binding.signOutButton.setOnClickListener {
            signOut()
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            categories -> adapter.submitList(categories)
        }

        binding.categoriesSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.filterCategories(query)
                return true
            }

        })

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