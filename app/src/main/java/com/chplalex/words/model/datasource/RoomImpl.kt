package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.data.Meanings
import com.chplalex.words.model.data.Translation

class RoomImpl : IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = listOf(
        DataModel(
            word,
            arrayListOf(
                Meanings(
                    Translation("Locale repository is not implemented yet"),
                    null
                )
            )
        )
    )
}
