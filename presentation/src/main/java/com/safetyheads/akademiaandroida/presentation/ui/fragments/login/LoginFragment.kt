package com.safetyheads.akademiaandroida.presentation.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentLoginBinding
import com.safetyheads.akademiaandroida.presentation.ui.components.snackbar.LoginSnackBar
import com.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.PasswordValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.isCorrectText

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
        val isSuccessFromForgotPass = arguments?.getBoolean("isSuccess")

        if (isSuccessFromForgotPass == true) {
            LoginSnackBar.make(
                binding.root,
                message = requireActivity().getString(R.string.we_have_emailed_your_password_reset_link)
            ).show()
        }

        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())

        binding.buttonSignIn.setOnClickListener {
            if(binding.eTextPassword.isCorrectText() && binding.eTextPassword.isCorrectText()) {

            }
        }

        binding.forgotPassword.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(false)
            findNavController().navigate(action)
        }
    }
}
