package com.safetyheads.akademiaandroida.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashScreenBinding.inflate(layoutInflater)

        return binding.root
    }

}