package com.safetyheads.akademiaandroida.presentation.ui.uistate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentCompanyTabsBinding

class CompanyTabs : Fragment() {

    private lateinit var binding: FragmentCompanyTabsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

}
