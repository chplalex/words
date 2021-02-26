package com.chplalex.words.di.component

import com.chplalex.words.di.module.*
import com.chplalex.words.di.module.ViewModelModule
import com.chplalex.words.ui.fragment.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)
@Singleton
interface AppComponent {

    fun inject(fragment: MainFragment)
}
