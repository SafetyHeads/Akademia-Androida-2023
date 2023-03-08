package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.databinding.FragmentSplashPlaceholderBinding

class SplashPlaceholder : Fragment() {

    private lateinit var binding: FragmentSplashPlaceholderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashPlaceholderBinding.inflate(inflater, container, false)
        return binding.root
    }
}