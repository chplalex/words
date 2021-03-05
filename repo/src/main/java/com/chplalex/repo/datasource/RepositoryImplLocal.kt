package com.chplalex.repo.datasource

import com.chplalex.words.contract.IDataSourceLocal
import com.chplalex.words.contract.IRepositoryLocal
import com.chplalex.model.AppState
import com.chplalex.model.DataModel

class RepositoryImplLocal(
    private val dataSource: IDataSourceLocal<List<com.chplalex.model.DataModel>>
) :
    IRepositoryLocal<List<com.chplalex.model.DataModel>> {

    override suspend fun getData(word: String): List<com.chplalex.model.DataModel> = dataSource.getData(word)

    override suspend fun saveToDB(appState: com.chplalex.model.AppState) { dataSource.saveToDB(appState) }
}
