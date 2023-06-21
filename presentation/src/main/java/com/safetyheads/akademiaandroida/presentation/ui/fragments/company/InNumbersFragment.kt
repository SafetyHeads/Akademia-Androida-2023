package com.safetyheads.akademiaandroida.presentation.ui.fragments.company

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentInNumbersBinding


class InNumbersFragment : Fragment() {
    private lateinit var binding: FragmentInNumbersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInNumbersBinding.inflate(layoutInflater)
        return binding.root
    }

}