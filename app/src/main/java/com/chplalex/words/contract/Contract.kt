package com.chplalex.words.contract

import com.chplalex.model.AppState

interface IView {
    fun renderData(appState: com.chplalex.model.AppState)
}

interface IPresenter<T: com.chplalex.model.AppState, V: IView> {
    fun attachView(view: IView)
    fun detachView(view: IView)
    fun getData(word: String, isOnline: Boolean)
}

interface IRepository<T> {
    suspend fun getData(word: String): T
}

interface IRepositoryLocal<T> : IRepository<T> {
    suspend fun saveToDB(appState: com.chplalex.model.AppState)
}

interface IDataSource<T> {
    suspend fun getData(word: String): T
}

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun saveToDB(appState: com.chplalex.model.AppState)
}