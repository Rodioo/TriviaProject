package com.example.triviaproject.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaproject.R

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

class CategoriesAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<String>("ceva", "ceva1", "c3va2")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView

        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item
    }

}