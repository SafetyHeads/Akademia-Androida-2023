package com.safetyheads.akademiaandroida.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.ActivityDashboardBinding
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController
    private val viewModel : DashboardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        viewModel.getSessionInfo()
    }

    private fun initObservers() {
        viewModel.isExistUser.observe(this) {
            setupNavBar(it)
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
            if(isLoggedUser) R.navigation.dashboard_logged_nav_graph
            else R.navigation.dashboard_not_logged_nav_graph
        )
        val bottomNav = binding.bottomNavigationView
        bottomNav.setupWithNavController(navController)
    }
}