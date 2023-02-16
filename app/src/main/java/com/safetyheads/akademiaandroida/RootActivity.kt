package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class RootActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val navigationAction = R.id.action_splashScreen_to_launchScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(navigationAction)
        }, SPLASH_TIME)

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

    companion object {
        private const val SPLASH_TIME = 3000L
    }
}
