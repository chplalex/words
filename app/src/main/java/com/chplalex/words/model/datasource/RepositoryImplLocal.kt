package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IDataSourceLocal
import com.chplalex.words.contract.IRepositoryLocal
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel

class RepositoryImplLocal(
    private val dataSource: IDataSourceLocal<List<DataModel>>
) :
    IRepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)

    override suspend fun saveToDB(appState: AppState) { dataSource.saveToDB(appState) }
}
