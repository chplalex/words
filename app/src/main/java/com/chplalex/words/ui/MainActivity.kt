package com.chplalex.words.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chplalex.words.R

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}
