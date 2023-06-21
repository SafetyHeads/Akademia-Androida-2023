package com.safetyheads.akademiaandroida.presentation.ui.fragments.wearehiring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentWeAreHiringBinding

class WeAreHiringFragment : Fragment() {

    private lateinit var binding: FragmentWeAreHiringBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeAreHiringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {

        binding.applyNow.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notLoggedCareer)
        }
    }
}
