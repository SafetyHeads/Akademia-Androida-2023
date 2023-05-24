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
import com.safetyheads.akademiaandroida.presentation.ui.utils.isCorrectText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        val etConfirmPassword = binding.eTextConfirmPassword
        val etPassword = binding.eTextPassword
        val etFullName = binding.eTextFullName
        val etEmailAdress = binding.eTextEmailAddress

        binding.buttonSignUp.setOnClickListener {
            if (etConfirmPassword.isCorrectText() && etPassword.isCorrectText()
                && etFullName.isCorrectText() && etEmailAdress.isCorrectText()
            ) {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.signUp(
                    etFullName.text.toString(),
                    etEmailAdress.text.toString(),
                    etPassword.text.toString(),
                )
            }
        }

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

    private fun setupInputValidation() {

        FullNameValidator.attach(binding.eTextFullName, requireContext())
        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())
        PasswordValidator.attach(binding.eTextConfirmPassword, requireContext())


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //no-op
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.buttonSignUp.isEnabled = isValidInput()
            }

            override fun afterTextChanged(s: Editable) {
                //no-op
            }
        }

        binding.eTextFullName.addTextChangedListener(textWatcher)
        binding.eTextEmailAddress.addTextChangedListener(textWatcher)
        binding.eTextPassword.addTextChangedListener(textWatcher)
        binding.eTextConfirmPassword.addTextChangedListener(textWatcher)

        binding.buttonSignUp.isEnabled = false


    }


    private fun isValidInput(): Boolean {
        Log.d("Backspace", "---------------------------------------")
        Log.d("eTextFullName", binding.eTextFullName.isCorrectText().toString())
        Log.d("eTextEmailAddress", binding.eTextEmailAddress.isCorrectText().toString())
        Log.d("eTextPassword", binding.eTextPassword.isCorrectText().toString())
        Log.d("eTextConfirmPassword", binding.eTextConfirmPassword.isCorrectText().toString())
        Log.d("CHECKPASSWORDS", (binding.eTextPassword.text.toString()
            .trim() == binding.eTextConfirmPassword.text.toString().trim()).toString())
        return binding.eTextFullName.isCorrectText() && binding.eTextPassword.isCorrectText() &&
                binding.eTextEmailAddress.isCorrectText() &&
                binding.eTextPassword.isCorrectText() &&
                binding.eTextConfirmPassword.isCorrectText() &&
                binding.eTextPassword.text.toString()
                    .trim() == binding.eTextConfirmPassword.text.toString().trim()



    }
}

