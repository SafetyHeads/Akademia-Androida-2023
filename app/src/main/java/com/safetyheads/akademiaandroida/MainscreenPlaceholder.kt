package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class MainscreenPlaceholder : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mainscreen_placeholder, container, false)

        val loginButton = view.findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainscreen_placeholder_to_login_placeholder)
        }
        val notloggedButton = view.findViewById<Button>(R.id.notloggedin)
        notloggedButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_mainscreen_placeholder_to_notlogged_placeholder)
        }
        return view
    }
}
