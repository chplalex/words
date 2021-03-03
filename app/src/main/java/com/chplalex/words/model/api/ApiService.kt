package com.chplalex.words.model.api

import com.chplalex.words.model.data.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    suspend fun searchAsync(@Query("search") wordToSearch: String): List<DataModel>
}
