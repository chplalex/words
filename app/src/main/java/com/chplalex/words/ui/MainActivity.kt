package com.chplalex.words.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chplalex.words.R
import com.chplalex.words.ui.fragment.main.MainFragment

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Activity created first time
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, MainFragment())
                .commit()
        }
    }
}