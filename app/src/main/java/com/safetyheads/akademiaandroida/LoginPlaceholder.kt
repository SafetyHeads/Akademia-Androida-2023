package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.safetyheads.akademiaandroida.databinding.FragmentLoginPlaceholderBinding

class LoginPlaceholder : Fragment() {

    private lateinit var binding: FragmentLoginPlaceholderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginPlaceholderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_login_placeholder_to_dashboard_placeholder)
        }
    }
}