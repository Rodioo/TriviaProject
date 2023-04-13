package com.example.triviaproject.repository

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://the-trivia-api.com/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface TriviaApiService {

    @GET(value = "categories")
    suspend fun getCategories(): Response<String>
}

object TriviaApi {
    val retrofitService: TriviaApiService by lazy {
        retrofit.create(TriviaApiService::class.java)
    }
}