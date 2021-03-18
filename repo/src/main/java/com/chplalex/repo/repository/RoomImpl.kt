package com.chplalex.repo.repository

import com.chplalex.model.AppState
import com.chplalex.model.dto.DataModelDto
import com.chplalex.model.ui.DataModel
import com.chplalex.repo.contract.IDataSourceLocal
import com.chplalex.repo.database.HistoryDao
import com.chplalex.utils.mapHistoryListEntityToModel
import com.chplalex.utils.mapHistorySuccessToEntity

class RoomImpl(private val dao: HistoryDao) : IDataSourceLocal<List<DataModelDto>> {

    override suspend fun getData(word: String): List<DataModelDto> = mapHistoryListEntityToModel(dao.all())

    override suspend fun saveToDB(appState: AppState) { dao.insert(mapHistorySuccessToEntity(appState)) }
}
