package com.safetyheads.akademiaandroida.presentation.ui.customviews

import com.safetyheads.akademiaandroida.presentation.databinding.FragmentDashboardBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.safetyheads.akademiaandroida.presentation.R

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        binding.bottomNavigationView.menu.clear()
        binding.bottomNavigationView.inflateMenu(
            if (currentUser != null) R.menu.dashboard_logged_nav_menu
            else R.menu.dashboard_not_logged_nav_menu
        )
    }
}
