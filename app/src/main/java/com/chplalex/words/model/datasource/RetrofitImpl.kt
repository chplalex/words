package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.model.data.DataModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = getService(BaseInterceptor.interceptor)
        .searchAsync(word)
        .await()

    private fun getService(interceptor: Interceptor) = createRetrofit(interceptor)
        .create(ApiService::class.java)

    private fun createRetrofit(interceptor: Interceptor) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(createOkHttpClient(interceptor))
        .build()

    private fun createOkHttpClient(interceptor: Interceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    companion object {
        private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
    }
}
