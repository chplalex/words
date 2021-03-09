package com.chplalex.repo.datasource

import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import com.chplalex.repo.contract.IDataSourceLocal
import com.chplalex.repo.contract.IRepositoryLocal

class RepositoryImplLocal(
    private val dataSource: IDataSourceLocal<List<DataModel>>
) :
    IRepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = dataSource.getData(word)

    override suspend fun saveToDB(appState: AppState) { dataSource.saveToDB(appState) }
}
