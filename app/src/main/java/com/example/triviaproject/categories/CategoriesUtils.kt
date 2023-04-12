package com.example.triviaproject.categories

import org.json.JSONObject

class CategoriesUtils {
    companion object {
        fun parseResponse(jsonString: String): List<Category> {
            val jsonObject = JSONObject(jsonString)
            val categoryList = mutableListOf<Category>()

            for (category in jsonObject.keys()) {
                val jsonArray = jsonObject.getJSONArray(category)
                val subcategories = mutableListOf<String>()

                for (i in 0 until jsonArray.length()) {
                    subcategories.add(jsonArray.getString(i))
                }

                categoryList.add(Category(category, subcategories))
            }
            return categoryList
        }
    }
}