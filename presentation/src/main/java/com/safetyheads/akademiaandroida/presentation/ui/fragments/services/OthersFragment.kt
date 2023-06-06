package com.safetyheads.akademiaandroida.presentation.ui.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentOthersBinding


class OthersFragment : Fragment() {

    private lateinit var binding: FragmentOthersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOthersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.serviceView.setImage(R.drawable.services_others)
        binding.serviceView.setText(
            R.string.ai_machine_learning_nlp,
            R.string.internet_of_things,
            R.string.cloud_devops
        )
    }

}