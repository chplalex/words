package com.chplalex.words

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

private const val DYNAMIC_FEATURE_ACTIVITY_PATH = "com.chplalex.dynamic.DynamicActivity"
private const val DYNAMIC_FEATURE_NAME = "Dynamic feature"

open class TranslatorActivity : AppCompatActivity() {

    private val navController by inject<NavController> { parametersOf(this) }

    private lateinit var splitInstallManager: SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.translator_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_dynamic_feature) {
            onDynamicFeature()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun onDynamicFeature() {
        splitInstallManager = SplitInstallManagerFactory.create(this)

        val request = SplitInstallRequest
            .newBuilder()
            .addModule(DYNAMIC_FEATURE_NAME)
            .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener { startActivity(Intent().setClassName(packageName, DYNAMIC_FEATURE_ACTIVITY_PATH)) }
            .addOnFailureListener { showError("Couldn't download feature: ", it) }
    }

    private fun showError(title: String, error: Throwable) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(error.message)
            .create()
            .show()
    }
}
