package com.chplalex.repo.contract

import com.chplalex.model.data.AppState

interface IView {
    fun renderData(appState: AppState)
}

interface IPresenter<T: AppState, V: IView> {
    fun attachView(view: IView)
    fun detachView(view: IView)
    fun getData(word: String, isOnline: Boolean)
}

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