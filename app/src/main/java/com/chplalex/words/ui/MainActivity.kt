package com.chplalex.words.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chplalex.words.R
import com.chplalex.words.ui.fragment.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, MainFragment())
            .commit()
    }
}