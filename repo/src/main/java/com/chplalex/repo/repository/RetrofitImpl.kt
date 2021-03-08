package com.chplalex.repo.repository

import com.chplalex.model.data.DataModel
import com.chplalex.repo.api.ApiService
import com.chplalex.repo.contract.IDataSource

class RetrofitImpl(private val service: ApiService) : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = service.searchAsync(word)
}
