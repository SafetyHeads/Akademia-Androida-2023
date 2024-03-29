package com.safetyheads.akademiaandroida.presentation.ui.fragments.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentClientsBinding

class ClientsFragment : Fragment() {

    private lateinit var binding: FragmentClientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClientsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
