package com.chplalex.words.di.module

import com.chplalex.words.contract.IDataSource
import com.chplalex.words.contract.IRepository
import com.chplalex.words.di.NAME_LOCAL
import com.chplalex.words.di.NAME_REMOTE
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.datasource.MainInteractor
import com.chplalex.words.model.datasource.RepositoryImpl
import com.chplalex.words.model.datasource.RetrofitImpl
import com.chplalex.words.model.datasource.RoomImpl
import com.chplalex.words.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<IDataSource<List<DataModel>>>(named(NAME_REMOTE)) { RetrofitImpl() }
    single<IDataSource<List<DataModel>>>(named(NAME_LOCAL)) { RoomImpl() }

    single<IRepository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImpl(get(named(NAME_REMOTE))) }
    single<IRepository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImpl(get(named(NAME_LOCAL))) }
}

val mainFragment = module {
    factory { MainInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    viewModel { MainViewModel(get()) }
}

