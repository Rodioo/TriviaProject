package com.example.triviaproject.categories


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaproject.databinding.TextItemViewBinding


class CategoriesAdapter(private val viewModel: CategoriesViewModel): ListAdapter<Category, CategoriesAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: TextItemViewBinding): RecyclerView.ViewHolder(binding.root) {

        val button = binding.categoryButton
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)

        holder.bind(category)

        holder.button.setOnClickListener {
            viewModel.selectedCategory.value = category
        }
    }

}