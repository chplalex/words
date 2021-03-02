package com.chplalex.words.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.chplalex.words.R
import com.chplalex.words.contract.IDataSource
import com.chplalex.words.contract.IRepository
import com.chplalex.words.contract.IRepositoryLocal
import com.chplalex.words.di.NAME_LOCAL
import com.chplalex.words.di.NAME_REMOTE
import com.chplalex.words.model.data.DataModel
import com.chplalex.words.ui.fragment.history.HistoryInteractor
import com.chplalex.words.ui.fragment.main.MainInteractor
import com.chplalex.words.model.datasource.RepositoryImpl
import com.chplalex.words.model.repository.RetrofitImpl
import com.chplalex.words.model.database.HistoryDatabase
import com.chplalex.words.model.datasource.RepositoryImplLocal
import com.chplalex.words.model.repository.RoomImpl
import com.chplalex.words.viewmodel.HistoryViewModel
import com.chplalex.words.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB").build() }
    single { get<HistoryDatabase>().historyDao() }

    single<IRepository<List<DataModel>>> { RepositoryImpl(RetrofitImpl()) }
    single<IRepositoryLocal<List<DataModel>>> { RepositoryImplLocal(RoomImpl(get())) }
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