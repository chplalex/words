package com.chplalex.repo.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val HISTORY_DB_NAME = "HistoryDB"

@Database(entities = arrayOf(HistoryEntity::class), version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}