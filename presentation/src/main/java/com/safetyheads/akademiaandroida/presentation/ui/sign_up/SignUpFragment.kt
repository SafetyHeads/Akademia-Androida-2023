package com.safetyheads.akademiaandroida.presentation.ui.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentSignUpBinding
import com.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.FullNameValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.PasswordValidator
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        val etConfirmPassword = binding.eTextConfirmPassword
        val etPassword = binding.eTextPassword
        val etFullName = binding.eTextFullName
        val etEmailAdress = binding.eTextEmailAddress

        binding.buttonSignUp.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            //TODO: add check is correct to EditText Function
            if (etPassword.error.isNullOrEmpty() &&
                etConfirmPassword.error.isNullOrEmpty() &&
                etFullName.error.isNullOrEmpty() &&
                etEmailAdress.error.isNullOrEmpty() && etPassword.text?.isNotBlank() == true &&
                etConfirmPassword.text?.isNotBlank() == true &&
                etEmailAdress.text?.isNotBlank() == true &&
                etFullName.text?.isNotBlank() == true
            ) {
                viewModel.signUp(
                    binding.eTextFullName.text.toString(),
                    binding.eTextEmailAddress.text.toString(),
                    binding.eTextPassword.text.toString(),
                )
            }

        }
        FullNameValidator.attach(etFullName, requireContext())
        EmailValidator.attach(etEmailAdress)
        PasswordValidator.attach(etPassword, requireContext())
        PasswordValidator.attach(etConfirmPassword, requireContext())


        lifecycleScope.launchWhenStarted {
            viewModel.registrationState.collect { result ->
                when {
                    result == null -> {
                    }

                    result.isSuccess -> {
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    result.isFailure -> {
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}
