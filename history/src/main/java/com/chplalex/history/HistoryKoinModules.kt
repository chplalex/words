package com.chplalex.history

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(historyFragment))
}

val historyFragment = module {
    scope(named<HistoryFragment>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}