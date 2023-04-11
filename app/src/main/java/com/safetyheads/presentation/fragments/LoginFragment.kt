package com.safetyheads.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.databinding.FragmentLoginBinding
import com.safetyheads.presentation.utils.EmailValidator
import com.safetyheads.presentation.utils.PasswordValidator

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())
    }
}
