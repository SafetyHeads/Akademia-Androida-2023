package com.safetyheads.akademiaandroida.presentation.ui.activities


import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.safetyheads.akademiaandroida.presentation.services.LocationForegroundService
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.ActivityDashboardBinding
import com.safetyheads.akademiaandroida.presentation.services.LocationBackgroundService
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        viewModel.getSessionInfo()
        startTrucker()
    }

    private fun startTrucker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                ),
                0
            )
        }

        Intent(this, LocationForegroundService::class.java).apply {
            action = LocationForegroundService.ACTION_START
        }.let { intent ->
            ContextCompat.startForegroundService(this, intent)
        }

        Intent(this, LocationBackgroundService::class.java).apply {
            action = LocationBackgroundService.ACTION_START
        }.let { intent ->
            ContextCompat.startForegroundService(this, intent)
        }
    }

    private fun initObservers() {
        viewModel.doesExistUser.observe(this) {
            setupNavBar(it)
        }

        viewModel.isLogOut.observe(this) { isLogOut ->
            if (isLogOut) {
                val intent = Intent()
                intent.setClassName(
                    packageName,
                    "com.safetyheads.akademiaandroida.presentation.ui.MainActivity"
                )
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setupNavBar(isLoggedUser: Boolean) {
        binding.bottomNavigationView.menu.clear()
        binding.bottomNavigationView.inflateMenu(
            if (isLoggedUser) R.menu.dashboard_logged_nav_menu
            else R.menu.dashboard_not_logged_nav_menu
        )
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(
            if (isLoggedUser) R.navigation.dashboard_logged_nav_graph
            else R.navigation.dashboard_not_logged_nav_graph
        )
        val bottomNav = binding.bottomNavigationView
        bottomNav.setupWithNavController(navController)
    }

}