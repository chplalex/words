package com.chplalex.words.utils

import com.chplalex.words.model.data.AppState
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.data.Meanings

fun parseSearchResults(appState: AppState): AppState {
    val newSearchResults = arrayListOf<DataModel>()

    if (appState is AppState.Success) {
        val searchResults = appState.data
        if (!searchResults.isNullOrEmpty()) {
            for (searchResult in searchResults) {
                parseResult(searchResult, newSearchResults)
            }
        }
    }

    return AppState.Success(newSearchResults)
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
