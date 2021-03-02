package com.chplalex.words.model.repository

import com.chplalex.words.contract.IDataSourceLocal
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.database.HistoryDao
import com.chplalex.words.utils.mapHistoryListEntityToModel
import com.chplalex.words.utils.mapHistorySuccessToEntity

class RoomImpl(private val dao: HistoryDao) : IDataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = mapHistoryListEntityToModel(dao.all())

    override suspend fun saveToDB(appState: AppState) { dao.insert(mapHistorySuccessToEntity(appState)) }
}
