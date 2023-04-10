package com.safetyheads.akademiaandroida.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.databinding.FragmentLoginBinding
import com.safetyheads.akademiaandroida.utils.EmailValidator
import com.safetyheads.akademiaandroida.utils.FullNameValidator
import com.safetyheads.akademiaandroida.utils.PasswordValidator

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
