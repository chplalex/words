package com.chplalex.words.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.chplalex.history.HistoryInteractor
import com.chplalex.history.HistoryViewModel
import com.chplalex.main.main.MainInteractor
import com.chplalex.main.main.MainViewModel
import com.chplalex.model.data.DataModel
import com.chplalex.repo.api.ApiService
import com.chplalex.repo.api.BaseInterceptor
import com.chplalex.repo.api.createRetrofit
import com.chplalex.repo.contract.IRepository
import com.chplalex.repo.contract.IRepositoryLocal
import com.chplalex.repo.database.HISTORY_DB_NAME
import com.chplalex.repo.database.HistoryDao
import com.chplalex.repo.database.HistoryDatabase
import com.chplalex.repo.datasource.RepositoryImpl
import com.chplalex.repo.datasource.RepositoryImplLocal
import com.chplalex.repo.repository.RetrofitImpl
import com.chplalex.repo.repository.RoomImpl
import com.chplalex.words.R
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
    factory { HistoryInteractor(get(), get()) }
    viewModel { HistoryViewModel(get()) }
}