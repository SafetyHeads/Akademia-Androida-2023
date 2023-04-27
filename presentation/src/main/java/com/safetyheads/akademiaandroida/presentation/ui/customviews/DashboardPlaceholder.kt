package com.safetyheads.akademiaandroida.presentation.ui.customviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.databinding.FragmentDashboardPlaceholderBinding

class DashboardPlaceholder : Fragment() {

    private lateinit var binding: FragmentDashboardPlaceholderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardPlaceholderBinding.inflate(inflater, container, false)
        return binding.root
    }

}
