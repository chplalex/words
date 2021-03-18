package com.chplalex.repo.datasource

import com.chplalex.model.dto.DataModelDto
import com.chplalex.repo.contract.IDataSource
import com.chplalex.repo.contract.IRepository

class RepositoryImpl(
    private val dataSource: IDataSource<List<DataModelDto>>
) :
    IRepository<List<DataModelDto>> {

    override suspend fun getData(word: String): List<DataModelDto> = dataSource.getData(word)
}
