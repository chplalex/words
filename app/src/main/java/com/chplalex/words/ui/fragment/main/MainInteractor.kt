package com.chplalex.words.ui.fragment.main

import com.chplalex.base.IInteractor
import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import com.chplalex.repo.contract.IRepository
import com.chplalex.repo.contract.IRepositoryLocal

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepositoryLocal<List<DataModel>>
) :
    IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }
}
