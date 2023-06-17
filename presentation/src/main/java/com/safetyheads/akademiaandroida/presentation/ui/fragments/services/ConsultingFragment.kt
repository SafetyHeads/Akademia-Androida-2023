package com.safetyheads.akademiaandroida.presentation.ui.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentConsultingBinding


class ConsultingFragment : Fragment() {

    private lateinit var binding: FragmentConsultingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConsultingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.serviceView.setImage(R.drawable.services_consulting)
        binding.serviceView.setText(
            R.string.cybersecurity,
            R.string.it_audits,
            R.string.digital_transformation
        )
    }

}