package com.chplalex.repo.repository

import com.chplalex.words.contract.IDataSourceLocal
import com.chplalex.model.AppState
import com.chplalex.model.DataModel
import com.chplalex.repo.database.HistoryDao
import com.chplalex.utils.mapHistoryListEntityToModel
import com.chplalex.utils.mapHistorySuccessToEntity

class RoomImpl(private val dao: HistoryDao) : IDataSourceLocal<List<com.chplalex.model.DataModel>> {

    override suspend fun getData(word: String): List<com.chplalex.model.DataModel> =
        com.chplalex.utils.mapHistoryListEntityToModel(dao.all())

    override suspend fun saveToDB(appState: com.chplalex.model.AppState) { dao.insert(com.chplalex.utils.mapHistorySuccessToEntity(appState)) }
}
