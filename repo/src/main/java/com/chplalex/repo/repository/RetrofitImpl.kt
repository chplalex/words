package com.chplalex.repo.repository

import com.chplalex.words.contract.IDataSource
import com.chplalex.model.DataModel
import com.chplalex.repo.api.ApiService

class RetrofitImpl(private val service: ApiService) : IDataSource<List<com.chplalex.model.DataModel>> {

    override suspend fun getData(word: String): List<com.chplalex.model.DataModel> = service.searchAsync(word)

//    override suspend fun getData(word: String): List<DataModel> = getService(BaseInterceptor.interceptor)
//        .searchAsync(word)

//    private fun getService(interceptor: Interceptor) = createRetrofit(interceptor).create(ApiService::class.java)

//    private fun createRetrofit(interceptor: Interceptor) = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(createOkHttpClient(interceptor))
//        .build()

//    private fun createOkHttpClient(interceptor: Interceptor) = OkHttpClient.Builder()
//        .addInterceptor(interceptor)
//        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//        .build()

//    companion object {
//        private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"
//    }
}
