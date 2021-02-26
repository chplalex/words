package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.contract.IRepository
import com.chplalex.words.model.data.DataModel

class RepositoryImpl(
    private val dataSource: IDataSource<List<DataModel>>
) :
    IRepository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)
}
