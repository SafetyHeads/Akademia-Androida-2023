package com.safetyheads.akademiaandroida.presentation.ui.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentDevelopmentBinding


class DevelopmentFragment : Fragment() {

    private lateinit var binding: FragmentDevelopmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDevelopmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.serviceView.setImage(R.drawable.services_development)
        binding.serviceView.setText(
            R.string.mobile_apps_development,
            R.string.web_apps_development,
            R.string.low_code_development
        )
    }
}