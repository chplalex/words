package com.chplalex.repo.datasource

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.contract.IRepository
import com.chplalex.model.DataModel

class RepositoryImpl(
    private val dataSource: IDataSource<List<com.chplalex.model.DataModel>>
) :
    IRepository<List<com.chplalex.model.DataModel>> {

    override suspend fun getData(word: String): List<com.chplalex.model.DataModel> = dataSource.getData(word)
}
