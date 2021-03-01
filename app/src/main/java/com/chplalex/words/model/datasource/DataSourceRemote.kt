package com.chplalex.words.model.datasource

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.model.data.DataModel

class DataSourceRemote(
    private val remoteProvider: RetrofitImpl = RetrofitImpl()
) :
    IDataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}
