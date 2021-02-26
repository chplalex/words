package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.contract.IRepository
import com.chplalex.words.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class RepositoryImpl(
    private val dataSource: IDataSource<List<DataModel>>
) :
    IRepository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = dataSource.getData(word)
}
