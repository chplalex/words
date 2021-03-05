package com.chplalex.words.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.chplalex.words.R
import com.chplalex.words.contract.IRepository
import com.chplalex.words.contract.IRepositoryLocal
import com.chplalex.repo.api.ApiService
import com.chplalex.repo.api.BaseInterceptor
import com.chplalex.repo.api.createRetrofit
import com.chplalex.model.DataModel
import com.chplalex.repo.database.HISTORY_DB_NAME
import com.chplalex.repo.database.HistoryDao
import com.chplalex.history.HistoryInteractor
import com.chplalex.words.ui.fragment.main.MainInteractor
import com.chplalex.repo.datasource.RepositoryImpl
import com.chplalex.repo.repository.RetrofitImpl
import com.chplalex.repo.database.HistoryDatabase
import com.chplalex.repo.datasource.RepositoryImplLocal
import com.chplalex.repo.repository.RoomImpl
import com.chplalex.history.HistoryViewModel
import com.chplalex.words.ui.fragment.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {
    // (Remote repository == Retrofit) DI
    single<com.chplalex.repo.api.ApiService> { com.chplalex.repo.api.createRetrofit(com.chplalex.repo.api.BaseInterceptor.interceptor)
        .create(com.chplalex.repo.api.ApiService::class.java) }
    single<IRepository<List<com.chplalex.model.DataModel>>> {
        com.chplalex.repo.datasource.RepositoryImpl(
            com.chplalex.repo.repository.RetrofitImpl(
                get<com.chplalex.repo.api.ApiService>()
            )
        )
    }

    // (Local repository = Room) DI
    single<com.chplalex.repo.database.HistoryDatabase> { Room.databaseBuilder(get(), com.chplalex.repo.database.HistoryDatabase::class.java,
        com.chplalex.repo.database.HISTORY_DB_NAME
    ).build() }
    single<com.chplalex.repo.database.HistoryDao> { get<com.chplalex.repo.database.HistoryDatabase>().historyDao() }
    single<IRepositoryLocal<List<com.chplalex.model.DataModel>>> {
        com.chplalex.repo.datasource.RepositoryImplLocal(
            com.chplalex.repo.repository.RoomImpl(
                get<com.chplalex.repo.database.HistoryDao>()
            )
        )
    }
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
    factory { com.chplalex.history.HistoryViewModel(get()) }
    factory { com.chplalex.history.HistoryInteractor(get(), get()) }
}