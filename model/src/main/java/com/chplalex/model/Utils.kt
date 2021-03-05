package com.chplalex.utils

import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import com.chplalex.model.data.Meanings

fun parseSearchResults(appState: AppState, isOnline: Boolean) = AppState.Success(mapResult(appState, isOnline))

private fun mapResult(state: AppState, isOnline: Boolean): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    if (state is AppState.Success) { getSuccessResultData(state, isOnline, newSearchResults) }
    return newSearchResults
}

private fun getSuccessResultData(state: AppState.Success, isOnline: Boolean, newSearchResults: ArrayList<DataModel>) {
    val searchResults: List<DataModel> = state.data as List<DataModel>
    if (searchResults.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchResults) { parseResult(searchResult, newSearchResults) }
        } else {
            for (searchResult in searchResults) { newSearchResults.add(DataModel(searchResult.word, arrayListOf())) }
        }
    }
}

private fun parseResult(dataModel: DataModel, newDataModels: ArrayList<DataModel>) {

    if (dataModel.word.isNullOrBlank() || dataModel.meanings.isNullOrEmpty()) {
        return
    }

    val newMeanings = arrayListOf<Meanings>()

    for (meaning in dataModel.meanings) {
        if (meaning.translation == null || meaning.translation.text.isNullOrBlank()) {
            continue
        }
        newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
    }

    if (newMeanings.isNotEmpty()) {
        newDataModels.add(DataModel(dataModel.word, newMeanings))
    }
}

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.text, ", ")
        } else {
            meaning.translation?.text
        }
    }
    return meaningsSeparatedByComma
}