package com.chplalex.words

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
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
import com.chplalex.utils.network.OnlineLiveData
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application))
}

val application = module {
    // (Remote repository == Retrofit) DI
    single<ApiService> { createRetrofit(BaseInterceptor.interceptor).create(ApiService::class.java) }
    single<IRepository<List<DataModel>>> { RepositoryImpl(RetrofitImpl(get<ApiService>())) }
    // (Local repository = Room) DI
    single<HistoryDatabase> { Room.databaseBuilder(get(), HistoryDatabase::class.java, HISTORY_DB_NAME).build() }
    single<HistoryDao> { get<HistoryDatabase>().historyDao() }
    single<IRepositoryLocal<List<DataModel>>> { RepositoryImplLocal(RoomImpl(get<HistoryDao>())) }
    // OnlineLiveData
    single<OnlineLiveData> { OnlineLiveData(androidContext()) }
    // NavController
    single<NavController> { (activity: TranslatorActivity) ->
        val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return@single navHostFragment.navController
    }
}