package com.example.triviaproject.repository

import com.example.triviaproject.question.Question
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://the-trivia-api.com/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitScalars = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

private val retrofitMoshi = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TriviaApiService {
    @GET(value = "categories")
    suspend fun getCategories(): Response<String>

    @GET(value = "questions")
    suspend fun getQuestions(
        @Query("categories") joinedCategories: String,
        @Query("difficulties") difficulty: String,
        @Query("limit") limit: Int,
        @Query("types") type: String
    ): Response<List<Question>>
}

object TriviaApi {
    val retrofitScalarsService: TriviaApiService by lazy {
        retrofitScalars.create(TriviaApiService::class.java)
    }
    val retrofitMoshiService: TriviaApiService by lazy {
        retrofitMoshi.create(TriviaApiService::class.java)
    }
}