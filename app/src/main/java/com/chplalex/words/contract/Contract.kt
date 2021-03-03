package com.chplalex.words.contract

import com.chplalex.words.model.data.AppState

interface IView {
    fun renderData(appState: AppState)
}

interface IPresenter<T: AppState, V: IView> {
    fun attachView(view: IView)
    fun detachView(view: IView)
    fun getData(word: String, isOnline: Boolean)
}

interface IInteractor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
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