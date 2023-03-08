package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.safetyheads.akademiaandroida.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityRootBinding
    private val navigationAction = R.id.action_splashScreen_to_launchScreenFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate(navigationAction)
        }, SPLASH_TIME)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id == R.id.launchScreenFragment || navController.currentDestination?.id == R.id.dashboard_placeholder) {
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
