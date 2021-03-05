package com.chplalex.words.ui.fragment.main

import androidx.lifecycle.LiveData
import com.chplalex.model.AppState
import com.chplalex.base.BaseViewModel
import com.chplalex.utils.parseSearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor) : com.chplalex.base.BaseViewModel<com.chplalex.model.AppState>() {

    private val liveDataForViewToObserve: LiveData<com.chplalex.model.AppState> = _mutableLiveData

    fun subscribe() = liveDataForViewToObserve

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = com.chplalex.model.AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(com.chplalex.utils.parseSearchResults(interactor.getData(word, isOnline), isOnline))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(com.chplalex.model.AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = com.chplalex.model.AppState.Success(null)
        super.onCleared()
    }
}