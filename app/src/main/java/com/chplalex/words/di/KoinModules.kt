package com.chplalex.words.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.chplalex.words.R
import com.chplalex.words.contract.IRepository
import com.chplalex.words.contract.IRepositoryLocal
import com.chplalex.words.model.api.ApiService
import com.chplalex.words.model.api.BaseInterceptor
import com.chplalex.words.model.api.createRetrofit
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.model.database.HISTORY_DB_NAME
import com.chplalex.words.model.database.HistoryDao
import com.chplalex.words.ui.fragment.history.HistoryInteractor
import com.chplalex.words.ui.fragment.main.MainInteractor
import com.chplalex.words.model.datasource.RepositoryImpl
import com.chplalex.words.model.repository.RetrofitImpl
import com.chplalex.words.model.database.HistoryDatabase
import com.chplalex.words.model.datasource.RepositoryImplLocal
import com.chplalex.words.model.repository.RoomImpl
import com.chplalex.words.ui.fragment.history.HistoryViewModel
import com.chplalex.words.ui.fragment.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    // (Remote repository == Retrofit) DI
    single<ApiService> { createRetrofit(BaseInterceptor.interceptor).create(ApiService::class.java) }
    single<IRepository<List<DataModel>>> { RepositoryImpl(RetrofitImpl(get<ApiService>())) }

    // (Local repository = Room) DI
    single<HistoryDatabase> { Room.databaseBuilder(get(), HistoryDatabase::class.java, HISTORY_DB_NAME).build() }
    single<HistoryDao> { get<HistoryDatabase>().historyDao() }
    single<IRepositoryLocal<List<DataModel>>> { RepositoryImplLocal(RoomImpl(get<HistoryDao>())) }
}

val navigation = module {
    single<NavController> { (activity: AppCompatActivity) ->
        (activity
            .supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController
    }
}

val mainFragment = module {
    factory { MainInteractor(get(), get()) }
    viewModel { MainViewModel(get()) }
}

val historyFragment = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(), get()) }
}