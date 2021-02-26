package com.chplalex.words.mvp.model.datasource

import com.chplalex.words.mvp.contract.IDataSource
import com.chplalex.words.mvp.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class DataSourceRemote(
    private val remoteProvider: RetrofitImpl = RetrofitImpl()
) :
    IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
