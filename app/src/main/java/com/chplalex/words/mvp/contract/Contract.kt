package com.chplalex.words.mvp.contract

import com.chplalex.words.mvp.model.data.AppState
import io.reactivex.rxjava3.core.Observable

interface IView {
    fun renderData(appState: AppState)
}

interface IPresenter<T: AppState, V: IView> {
    fun attachView(view: IView)
    fun detachView(view: IView)
    fun getData(word: String, isOnline: Boolean)
}

interface IInteractor<T> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}

interface IRepository<T> {
    fun getData(word: String): Observable<T>
}

interface IDataSource<T> {
    fun getData(word: String): Observable<T>
}