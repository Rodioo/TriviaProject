package com.example.triviaproject.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val name: String,
    val subcategories: List<String>
    ) : Parcelable