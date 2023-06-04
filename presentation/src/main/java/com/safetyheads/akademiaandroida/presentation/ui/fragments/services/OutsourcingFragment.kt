package com.safetyheads.akademiaandroida.presentation.ui.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentOutsourcingBinding


class OutsourcingFragment : Fragment() {

    private lateinit var binding: FragmentOutsourcingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOutsourcingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.serviceView.setImage(R.drawable.services_outsorcing)
        binding.serviceView.setText(
            R.string.team_augmentation,
            R.string.project_team_leasing,
            R.string.support_and_maintenance
        )
    }
}