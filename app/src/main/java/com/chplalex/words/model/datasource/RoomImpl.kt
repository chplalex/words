package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.data.Meanings
import com.chplalex.words.model.data.Translation
import io.reactivex.rxjava3.core.Observable

class RoomImpl : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        return Observable.just(
            arrayListOf(
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
        )
    }
}
