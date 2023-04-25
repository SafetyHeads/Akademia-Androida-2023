package com.example.presentation.safetyheads.akademiaandroida.presentation.ui.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.databinding.FragmentSignUpBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO:
// https://trello.com/c/tbEmpUyh/112-podpiąć-progressbar-do-ekranu-rejestracji

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignUp.setOnClickListener {
            viewModel.signUp(
                binding.eTextFullName.toString(),
                binding.eTextEmailAddress.toString(),
                binding.eTextPassword.toString(),
                binding.eTextConfirmPassword.toString()
            )
        }
        com.example.presentation.safetyheads.akademiaandroida.presentation.ui.utils.FullNameValidator.attach(
            binding.eTextFullName,
            requireContext()
        )
        com.example.presentation.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator.attach(
            binding.eTextEmailAddress
        )
        com.example.presentation.safetyheads.akademiaandroida.presentation.ui.utils.PasswordValidator.attach(
            binding.eTextPassword,
            requireContext()
        )
        com.example.presentation.safetyheads.akademiaandroida.presentation.ui.utils.PasswordValidator.attach(
            binding.eTextConfirmPassword,
            requireContext()
        )
    }
}

