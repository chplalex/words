package com.chplalex.words.ui

import android.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chplalex.words.R
import com.chplalex.words.ui.fragment.main.MainFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, MainFragment())
            .commit()
    }

    override fun fragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}