package com.chplalex.words.ui

import android.app.Application
import com.chplalex.words.di.component.AppComponent
import com.chplalex.words.di.component.DaggerAppComponent

class TranslatorApp : Application() {

    companion object {
        lateinit var instance: TranslatorApp
    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder().build()
    }
}
