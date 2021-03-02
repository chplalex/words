package com.chplalex.words.ui.fragment.history

import com.chplalex.words.contract.IInteractor
import com.chplalex.words.contract.IRepository
import com.chplalex.words.contract.IRepositoryLocal
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel

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