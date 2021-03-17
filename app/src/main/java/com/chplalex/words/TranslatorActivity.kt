package com.chplalex.words

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

private const val DYNAMIC_FEATURE_ACTIVITY_PATH = "com.chplalex.dynamic.DynamicActivity"
private const val DYNAMIC_FEATURE_NAME = "dynamic"
private const val REQUEST_CODE = 8001

class TranslatorActivity : AppCompatActivity() {

    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var splitInstallManager: SplitInstallManager

    private val stateUpdatedListener = InstallStateUpdatedListener { state ->
        state?.let {
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                showCompleteUpdate()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkForUpdates()

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.setupWithNavController(navController)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                appUpdateManager.unregisterListener(stateUpdatedListener)
            } else {
                showError("App update failed!", Throwable("Result code is $resultCode"))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    showCompleteUpdate()
                }
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, this, REQUEST_CODE)
                }
            }
    }

    private fun onDynamicFeature() {
        splitInstallManager = SplitInstallManagerFactory.create(applicationContext)

        val request = SplitInstallRequest
            .newBuilder()
            .addModule(DYNAMIC_FEATURE_NAME)
            .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener { startActivity(Intent().setClassName(packageName, DYNAMIC_FEATURE_ACTIVITY_PATH)) }
            .addOnFailureListener { showError("Couldn't install feature: ", it) }
    }

    private fun checkForUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfo = appUpdateManager.appUpdateInfo
        appUpdateInfo.addOnSuccessListener { appUpdateIntent ->
            if (
                appUpdateIntent.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateIntent.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(appUpdateIntent, IMMEDIATE, this, REQUEST_CODE)
                return@addOnSuccessListener
            }
        }
    }

    private fun showError(title: String, error: Throwable) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(error.message)
            .create()
            .show()
    }

    private fun showCompleteUpdate() {
        MaterialAlertDialogBuilder(this)
            .setTitle("An update has just been downloaded")
            .setMessage("Would you like to reload applications to install the update?")
            .setPositiveButton(R.string.pos_button) { _, _ -> appUpdateManager.completeUpdate() }
            .setNegativeButton(R.string.neg_button, null)
            .create()
            .show()
    }
}
