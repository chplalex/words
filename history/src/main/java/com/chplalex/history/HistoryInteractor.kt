package com.chplalex.history

import com.chplalex.base.IInteractor
import com.chplalex.model.AppState
import com.chplalex.model.dto.DataModelDto
import com.chplalex.repo.contract.IRepository
import com.chplalex.repo.contract.IRepositoryLocal
import com.chplalex.utils.mapDataModelDtoToUi

class HistoryInteractor(
    private val repositoryRemote: IRepository<List<DataModelDto>>,
    private val repositoryLocal: IRepositoryLocal<List<DataModelDto>>
) : IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean) = AppState.Success(
        mapDataModelDtoToUi(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    )
}