package com.example.project2

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object RetrofitClient {
    private const val BASE_URL = "http://apis.data.go.kr/B551011/Odii/"

    private val client = OkHttpClient.Builder().build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val dataApiService: DataApiService = retrofit.create(DataApiService::class.java)
}
