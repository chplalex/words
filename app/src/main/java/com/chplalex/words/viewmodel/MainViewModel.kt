package com.chplalex.words.viewmodel

import androidx.lifecycle.LiveData
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.datasource.DataSourceLocal
import com.chplalex.words.model.datasource.DataSourceRemote
import com.chplalex.words.model.datasource.MainInteractor
import com.chplalex.words.model.datasource.RepositoryImpl
import io.reactivex.rxjava3.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) : BaseViewModel<AppState>() {

    private var appState: AppState? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { liveDataForViewToObserve.value = AppState.Loading(null) }
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    fun subscribe() = liveDataForViewToObserve

    private fun getObserver(): DisposableObserver<AppState> = object : DisposableObserver<AppState>() {
        override fun onNext(state: AppState) {
            appState = state
            liveDataForViewToObserve.value = state
        }

        override fun onError(e: Throwable) {
            liveDataForViewToObserve.value = AppState.Error(e)
        }

        override fun onComplete() {}
    }
}