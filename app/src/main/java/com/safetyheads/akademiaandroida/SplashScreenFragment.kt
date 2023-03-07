package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
/*
        Handler(Looper.myLooper()!!).postDelayed(
            {
                findNavController().navigate(R.id.action_splashScreen_to_launchScreen)
            }, 3000
        )
*/


        return view
    }

}