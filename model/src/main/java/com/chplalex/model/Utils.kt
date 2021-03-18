package com.chplalex.utils

import com.chplalex.model.AppState
import com.chplalex.model.dto.*
import com.chplalex.model.ui.*

fun mapDataModelDtoToUi(searchResults: List<DataModelDto>): List<DataModel> {
    return searchResults.map { searchResult ->
        var meanings: List<Meaning> = listOf()
        searchResult.meanings?.let {
            meanings = it.map { meaningsDto ->
                Meaning(
                    Translation(meaningsDto?.translation?.text ?: ""),
                    meaningsDto?.imageUrl ?: ""
                )
            }
        }
        DataModel(searchResult.word ?: "", meanings)
    }
}

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

    if (dataModel.word.isEmpty() || dataModel.meanings.isEmpty()) {
        return
    }

    val newMeanings = arrayListOf<Meaning>()

    for (meaning in dataModel.meanings) {
        if (meaning.translation.translatedMeaning.isEmpty()) {
            continue
        }
        newMeanings.add(Meaning(meaning.translation, meaning.imageUrl))
    }

    if (newMeanings.isNotEmpty()) {
        newDataModels.add(DataModel(dataModel.word, newMeanings))
    }
}

fun convertMeaningsToString(meanings: List<Meaning>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation.translatedMeaning, ", ")
        } else {
            meaning.translation.translatedMeaning
        }
    }
    return meaningsSeparatedByComma
}

fun mapHistoryListEntityToModel(entities: List<HistoryEntity>): List<DataModelDto> {
    val models = arrayListOf<DataModelDto>()
    for (entity in entities) {
        models.add(DataModelDto(entity.word, arrayListOf(MeaningDto(TranslationDto(entity.description), entity.imageUrl))))
    }
    return models
}

fun mapHistorySuccessToEntity(appState: AppState): HistoryEntity {
    if (appState is AppState.Success) {
        if (appState.data.isNullOrEmpty()) {
            return HistoryEntity("", null, null)
        } else {
            return with(appState.data.get(0)) { HistoryEntity(word, description(), imageUrl()) }
        }
    } else {
        return HistoryEntity("", null, null)
    }
}
