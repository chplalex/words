package com.chplalex.history

fun mapHistoryListEntityToModel(entities: List<HistoryEntity>): List<DataModel> {
    val models = arrayListOf<DataModel>()
    for (entity in entities) {
        models.add(DataModel(entity.word, arrayListOf(Meanings(Translation(entity.description), entity.imageUrl))))
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