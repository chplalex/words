package com.chplalex.repo.contract

import com.chplalex.model.AppState

interface IRepository<T> {
    suspend fun getData(word: String): T
}

interface IRepositoryLocal<T> : IRepository<T> {
    suspend fun saveToDB(appState: AppState)
}

interface IDataSource<T> {
    suspend fun getData(word: String): T
}

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun saveToDB(appState: AppState)
}