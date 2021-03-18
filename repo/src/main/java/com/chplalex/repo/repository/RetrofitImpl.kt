package com.chplalex.repo.repository

import com.chplalex.model.dto.DataModelDto
import com.chplalex.repo.api.ApiService
import com.chplalex.repo.contract.IDataSource

class RetrofitImpl(private val service: ApiService) : IDataSource<List<DataModelDto>> {

    override suspend fun getData(word: String): List<DataModelDto> = service.searchAsync(word)
}
