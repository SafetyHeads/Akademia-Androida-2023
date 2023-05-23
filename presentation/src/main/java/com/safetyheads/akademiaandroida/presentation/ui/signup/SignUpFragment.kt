package com.safetyheads.akademiaandroida.presentation.ui.signup

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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

        setupInputValidation()

        binding.buttonSignUp.setOnClickListener {
            viewModel.signUp(
                binding.eTextFullName.text.toString(),
                binding.eTextEmailAddress.text.toString(),
                binding.eTextPassword.text.toString(),
            )
        }


        lifecycleScope.launchWhenStarted {
            viewModel.registrationState.collect { result ->
                when {
                    result == null -> {
                    }

                    result.isSuccess -> {

                    }

                    result.isFailure -> {

                    }
                }
            }
        }
    }

    private fun setupInputValidation() {

        FullNameValidator.attach(binding.eTextFullName, requireContext())
        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())
        PasswordValidator.attach(binding.eTextConfirmPassword, requireContext())


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d(ContentValues.TAG, "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.buttonSignUp.isEnabled = isValidInput()
                Log.d(ContentValues.TAG, "onTextChanged")
            }
            override fun afterTextChanged(s: Editable) {
                Log.d(ContentValues.TAG, "afterTextChanged")
            }
        }

        binding.eTextFullName.addTextChangedListener(textWatcher)
        binding.eTextEmailAddress.addTextChangedListener(textWatcher)
        binding.eTextPassword.addTextChangedListener(textWatcher)
        binding.eTextConfirmPassword.addTextChangedListener(textWatcher)

        binding.buttonSignUp.isEnabled = false


    }


    private fun isValidInput(): Boolean {
        val fullname = binding.eTextFullName.text.toString().trim()
        val email = binding.eTextEmailAddress.text.toString().trim()
        val password = binding.eTextPassword.text.toString().trim()
        val passwordconfirm = binding.eTextConfirmPassword.text.toString().trim()


        return email.isNotEmpty() && password.isNotEmpty() && fullname.isNotEmpty() && passwordconfirm.isNotEmpty()
                && password == passwordconfirm
    }
}

