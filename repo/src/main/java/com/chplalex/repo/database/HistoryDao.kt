package com.chplalex.repo.database

import androidx.room.*
import com.chplalex.model.data.HistoryEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity")
    suspend fun all(): List<HistoryEntity>
    @Query("SELECT * FROM HistoryEntity")
    fun getAll(): List<HistoryEntity>
    @Query("SELECT * FROM HistoryEntity WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): HistoryEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<HistoryEntity>)
    @Update
    suspend fun update(entity: HistoryEntity)
    @Delete
    suspend fun delete(entity: HistoryEntity)
}