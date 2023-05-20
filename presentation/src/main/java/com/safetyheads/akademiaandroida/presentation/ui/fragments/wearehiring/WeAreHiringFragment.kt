package com.safetyheads.akademiaandroida.presentation.ui.fragments.wearehiring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        binding.applyNow.setOnClickListener { }

        @Suppress("ForbiddenComment")
        binding.applyNow.setOnClickListener {
            // TODO: Apply now button click
        }
    }
}
