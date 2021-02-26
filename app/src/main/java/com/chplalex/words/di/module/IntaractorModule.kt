package com.chplalex.words.di.module

import com.chplalex.words.contract.IRepository
import com.chplalex.words.di.NAME_LOCAL
import com.chplalex.words.di.NAME_REMOTE
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.datasource.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: IRepository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: IRepository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}