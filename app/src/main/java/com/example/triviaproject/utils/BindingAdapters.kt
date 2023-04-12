package com.example.triviaproject.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaproject.categories.CategoriesAdapter
import com.example.triviaproject.categories.Category

@BindingAdapter("categoriesList")
fun bindCategoriesRecyclerView(recyclerView: RecyclerView, data: List<Category>?) {
    val adapter = recyclerView.adapter as CategoriesAdapter
    adapter.submitList(data)
}
