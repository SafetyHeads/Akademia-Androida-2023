package com.safetyheads.akademiaandroida.presentation.ui.fragments.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ServicesPagerAdapter(manager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DevelopmentFragment()
            1 -> OutsourcingFragment()
            2 -> ConsultingFragment()
            3 -> OthersFragment()
            else -> DevelopmentFragment()
        }
    }
}