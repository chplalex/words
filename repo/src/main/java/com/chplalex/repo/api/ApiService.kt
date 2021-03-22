package com.chplalex.repo.api

import com.chplalex.model.data.DataModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"

interface ApiService {
    @GET("words/search")
    suspend fun searchAsync(@Query("search") wordToSearch: String): List<DataModel>
}

fun createRetrofit(interceptor: Interceptor) = Retrofit.Builder()
    .baseUrl(API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(createOkHttpClient(interceptor))
    .build()

private fun createOkHttpClient(interceptor: Interceptor) = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()
