package com.example.triviaproject.categories

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triviaproject.MainActivity
import com.example.triviaproject.R
import com.example.triviaproject.databinding.FragmentCategoriesBinding
import com.example.triviaproject.utils.RecyclerViewSpacing
import com.example.triviaproject.utils.Spacing
import com.example.triviaproject.utils.getSpacing
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

        binding.categoriesRecycler.addItemDecoration(RecyclerViewSpacing(requireContext().getSpacing(Spacing.MEDIUM)))

        adapter = CategoriesAdapter(viewModel)
        binding.categoriesRecycler.adapter = adapter

        binding.signOutButton.setOnClickListener {
            signOut()
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            categories -> adapter.submitList(categories)
        }

        viewModel.selectedCategory.observe(viewLifecycleOwner) {
            view?.findNavController()?.navigate(CategoriesFragmentDirections.actionCategoriesFragmentToQuestionFragment(it))
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

        view?.findNavController()?.navigate(CategoriesFragmentDirections.actionCategoriesFragmentToLoginFragment())
    }
}