package com.safetyheads.akademiaandroida

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel

class RootActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val splashScreenViewModel: SplashScreenViewModel by viewModels { SplashScreenViewModel.factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        splashScreenViewModel.delaySplashScreen(navController)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id == R.id.mainscreen_placeholder || navController.currentDestination?.id == R.id.dashboard_placeholder) {
                    // do nothing
                } else {
                    navController.popBackStack()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

}
