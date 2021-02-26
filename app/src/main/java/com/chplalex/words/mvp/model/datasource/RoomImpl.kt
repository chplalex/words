package com.chplalex.words.mvp.model.datasource

import com.chplalex.words.mvp.contract.IDataSource
import com.chplalex.words.mvp.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class RoomImpl : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
