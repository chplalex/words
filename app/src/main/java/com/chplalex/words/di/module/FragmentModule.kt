package com.chplalex.words.di.module

import com.chplalex.words.ui.fragment.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMaiFragment(): MainFragment
}