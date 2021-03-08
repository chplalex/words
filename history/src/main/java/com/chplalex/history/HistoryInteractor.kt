package com.chplalex.history

import com.chplalex.base.IInteractor
import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import com.chplalex.repo.contract.IRepository
import com.chplalex.repo.contract.IRepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepositoryLocal<List<DataModel>>
) : IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean) = AppState.Success(
        if (fromRemoteSource) {
            repositoryRemote
        } else {
            repositoryLocal
        }.getData(word)
    )
}