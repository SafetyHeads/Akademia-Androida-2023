package com.safetyheads.akademiaandroida.presentation.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


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

}