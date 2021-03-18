package com.chplalex.repo.datasource

import com.chplalex.model.AppState
import com.chplalex.model.dto.DataModelDto
import com.chplalex.repo.contract.IDataSourceLocal
import com.chplalex.repo.contract.IRepositoryLocal

class RepositoryImplLocal(
    private val dataSource: IDataSourceLocal<List<DataModelDto>>
) :
    IRepositoryLocal<List<DataModelDto>> {

    override suspend fun getData(word: String): List<DataModelDto> = dataSource.getData(word)

    override suspend fun saveToDB(appState: AppState) { dataSource.saveToDB(appState) }
}
