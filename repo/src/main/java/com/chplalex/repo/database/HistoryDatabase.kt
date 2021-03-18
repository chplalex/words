package com.chplalex.repo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chplalex.model.dto.HistoryEntity

const val HISTORY_DB_NAME = "HistoryDB"

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}