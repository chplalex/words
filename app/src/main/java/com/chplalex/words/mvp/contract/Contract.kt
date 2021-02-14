package com.chplalex.words.mvp.contract

import com.chplalex.words.mvp.model.AppState
import io.reactivex.rxjava3.core.Observable

interface View {
    fun renderData(appState: AppState)
}

interface Presenter<T: AppState, V: View> {
    fun attachView(view: View)
    fun detachView(view: View)
    fun getData(word: String, isOnline: Boolean)
}

interface Interactor<T> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}

interface Repository<T> {
    fun getData(word: String): Observable<T>
}

interface DataSource<T> {
    fun getData(word: String): Observable<T>
}