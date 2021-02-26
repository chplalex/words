package com.chplalex.words.presenter

import com.chplalex.words.contract.IPresenter
import com.chplalex.words.contract.IView
import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.datasource.DataSourceLocal
import com.chplalex.words.model.datasource.DataSourceRemote
import com.chplalex.words.model.datasource.MainInteractor
import com.chplalex.words.model.datasource.RepositoryImpl
import com.chplalex.words.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver

class MainPresenterImlp<T : AppState, V : IView>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) :
    IPresenter<T, V> {

    private var currentView: IView? = null

    override fun attachView(view: IView) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: IView) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> = object : DisposableObserver<AppState>() {

        override fun onNext(appState: AppState) {
            currentView?.renderData(appState)
        }

        override fun onError(e: Throwable) {
            currentView?.renderData(AppState.Error(e))
        }

        override fun onComplete() {}
    }
}
