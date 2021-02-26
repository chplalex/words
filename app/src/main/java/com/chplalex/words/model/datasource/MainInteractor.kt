package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IInteractor
import com.chplalex.words.contract.IRepository
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepository<List<DataModel>>
) :
    IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState = AppState.Success(
        if (fromRemoteSource) { repositoryRemote } else { repositoryLocal } .getData(word)
    )
}
