package com.chplalex.dynamic

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

const val NASA_BASE_URL = "https://api.nasa.gov/"
const val NASA_DATE_PATTERN = "yyyy-MM-dd"

interface NasaApi {

    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY
    ): DynamicData
}

fun nasaApi() : NasaApi = Retrofit.Builder()
    .baseUrl(NASA_BASE_URL)
    .client(okHttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(NasaApi::class.java)

fun okHttpClient() = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor())
    .build()

fun httpLoggingInterceptor() = HttpLoggingInterceptor().also {
    it.level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}

fun Date.nasaDatePatternString(): String =
    SimpleDateFormat(NASA_DATE_PATTERN, Locale.getDefault()).format(this)

fun Date.nasaDatePatternThisDay() = nasaDatePatternString()
