package com.chplalex.words.mvp.model.datasource

import com.chplalex.words.mvp.contract.IDataSource
import com.chplalex.words.mvp.contract.IRepository
import com.chplalex.words.mvp.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class RepositoryImpl(
    private val dataSource: IDataSource<List<DataModel>>
) :
    IRepository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)
}
