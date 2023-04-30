package com.safetyheads.akademiaandroida.presentation.ui.customviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentNotloggedPlaceholderBinding

class NotloggedPlaceholder : Fragment() {

    private lateinit var binding: FragmentNotloggedPlaceholderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotloggedPlaceholderBinding.inflate(inflater, container, false)
        return binding.root
    }
}
