package com.chplalex.words

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.chplalex.main.main.MainViewModel
import com.chplalex.model.data.AppState
import com.chplalex.model.data.DataModel
import com.chplalex.repo.database.HISTORY_DB_NAME
import com.chplalex.repo.database.HistoryDao
import com.chplalex.repo.database.HistoryDatabase
import com.chplalex.repo.datasource.RepositoryImplLocal
import com.chplalex.repo.repository.RoomImpl
import com.chplalex.words.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
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
