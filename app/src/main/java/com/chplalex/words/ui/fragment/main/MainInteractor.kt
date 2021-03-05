package com.chplalex.words.ui.fragment.main

import com.chplalex.words.contract.IInteractor
import com.chplalex.words.contract.IRepository
import com.chplalex.words.contract.IRepositoryLocal
import com.chplalex.model.AppState
import com.chplalex.model.DataModel

class MainInteractor(
    private val repositoryRemote: IRepository<List<com.chplalex.model.DataModel>>,
    private val repositoryLocal: IRepositoryLocal<List<com.chplalex.model.DataModel>>
) :
    IInteractor<com.chplalex.model.AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.chplalex.model.AppState {
        val appState: com.chplalex.model.AppState
        if (fromRemoteSource) {
            appState = com.chplalex.model.AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = com.chplalex.model.AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }
}
