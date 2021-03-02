package com.chplalex.words.ui

import android.app.Application
import com.chplalex.words.di.module.application
import com.chplalex.words.di.module.mainFragment
import com.chplalex.words.di.module.navigation
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, navigation, mainFragment))
        }
    }
}
