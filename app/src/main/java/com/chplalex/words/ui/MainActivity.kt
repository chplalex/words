package com.chplalex.words.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.chplalex.words.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

open class MainActivity : AppCompatActivity() {

    private val navController by inject<NavController> { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.setupWithNavController(navController)
    }
}
