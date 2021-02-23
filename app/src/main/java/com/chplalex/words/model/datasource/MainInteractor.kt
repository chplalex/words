package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IInteractor
import com.chplalex.words.contract.IRepository
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepository<List<DataModel>>
) :
    IInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> = if (fromRemoteSource) {
        repositoryRemote.getData(word).map { AppState.Success(it) }
    } else {
        repositoryLocal.getData(word).map { AppState.Success(it) }
    }
}
