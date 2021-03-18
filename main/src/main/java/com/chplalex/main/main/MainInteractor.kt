package com.chplalex.main.main

import com.chplalex.base.IInteractor
import com.chplalex.model.AppState
import com.chplalex.model.dto.DataModelDto
import com.chplalex.repo.contract.IRepository
import com.chplalex.repo.contract.IRepositoryLocal
import com.chplalex.utils.mapDataModelDtoToUi

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModelDto>>,
    private val repositoryLocal: IRepositoryLocal<List<DataModelDto>>
) :
    IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(mapDataModelDtoToUi(repositoryRemote.getData(word)))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(mapDataModelDtoToUi(repositoryLocal.getData(word)))
        }
        return appState
    }
}
