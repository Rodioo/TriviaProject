package com.example.triviaproject.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaproject.R

enum class Spacing {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE,
}

fun Context.getSpacing(dimen: Spacing): Int {
    return when(dimen) {
        Spacing.SMALL -> this.resources.getDimensionPixelSize(R.dimen.small_margin)
        Spacing.MEDIUM -> this.resources.getDimensionPixelSize(R.dimen.medium_margin)
        Spacing.LARGE -> this.resources.getDimensionPixelSize(R.dimen.large_margin)
        Spacing.EXTRA_LARGE -> this.resources.getDimensionPixelSize(R.dimen.extra_large_margin)
    }
}

class RecyclerViewSpacing(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        // Add the spacing to the bottom of all items except the last one
        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = space
        }
    }
}
