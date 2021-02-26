package com.chplalex.words.di.module

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.contract.IRepository
import com.chplalex.words.di.NAME_LOCAL
import com.chplalex.words.di.NAME_REMOTE
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.datasource.RepositoryImpl
import com.chplalex.words.model.datasource.RetrofitImpl
import com.chplalex.words.model.datasource.RoomImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(
        @Named(NAME_REMOTE) dataSource: IDataSource<List<DataModel>>
    ):
        IRepository<List<DataModel>> = RepositoryImpl(dataSource)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(
        @Named(NAME_LOCAL) dataSource: IDataSource<List<DataModel>>
    ):
        IRepository<List<DataModel>> = RepositoryImpl(dataSource)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): IDataSource<List<DataModel>> = RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): IDataSource<List<DataModel>> = RoomImpl()
}