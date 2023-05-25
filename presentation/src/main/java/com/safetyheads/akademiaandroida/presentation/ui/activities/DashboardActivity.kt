package com.safetyheads.akademiaandroida.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavBar()
        setupBottomNavBar()
    }

    private fun setupBottomNavBar() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        binding.bottomNavigationView.menu.clear()
        binding.bottomNavigationView.inflateMenu(
            if (currentUser != null) R.menu.dashboard_logged_nav_menu
            else R.menu.dashboard_not_logged_nav_menu
        )
    }

    private fun setupNavBar() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNav = binding.bottomNavigationView
        bottomNav.setupWithNavController(navController)

    }
}