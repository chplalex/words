package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class DataSourceLocal(
    private val remoteProvider: RoomImpl = RoomImpl()
) :
    IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
