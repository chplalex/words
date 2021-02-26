package com.chplalex.words.ui

import android.app.Application
import com.chplalex.words.di.module.application
import com.chplalex.words.di.module.mainFragment
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(application, mainFragment))
        }
    }
}
