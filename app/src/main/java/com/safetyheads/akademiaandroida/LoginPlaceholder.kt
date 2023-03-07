package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class LoginPlaceholder : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login_placeholder, container, false)
        val loginButton = view.findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_login_placeholder_to_dashboard_placeholder)
        }
        return view
    }


}