package com.chplalex.words.di.component

import android.app.Application
import com.chplalex.words.di.module.ActivityModule
import com.chplalex.words.di.module.InteractorModule
import com.chplalex.words.di.module.RepositoryModule
import com.chplalex.words.di.module.ViewModelModule
import com.chplalex.words.ui.TranslatorApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: TranslatorApp)
}