package com.chplalex.repo.datasource

import com.chplalex.model.data.DataModel
import com.chplalex.repo.contract.IDataSource
import com.chplalex.repo.contract.IRepository

class RepositoryImpl(
    private val dataSource: IDataSource<List<DataModel>>
) :
    IRepository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)
}
