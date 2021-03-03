package com.chplalex.words.ui.fragment.history

import androidx.lifecycle.LiveData
import com.chplalex.words.model.data.AppState
import com.chplalex.words.ui.fragment.base.BaseViewModel
import com.chplalex.words.ui.fragment.history.HistoryInteractor
import com.chplalex.words.utils.parseSearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(private val interactor: HistoryInteractor) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe() = liveDataForViewToObserve

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(parseSearchResults(interactor.getData(word, isOnline), isOnline))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}