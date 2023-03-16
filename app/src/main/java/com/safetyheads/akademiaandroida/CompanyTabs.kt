package com.safetyheads.akademiaandroida

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.databinding.FragmentCompanyTabsBinding

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