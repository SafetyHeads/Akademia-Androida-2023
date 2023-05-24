package com.safetyheads.akademiaandroida.presentation.ui.fragments.login

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentLoginBinding
import com.safetyheads.akademiaandroida.presentation.ui.components.snackbar.LoginSnackBar
import com.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.PasswordValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.isCorrectText
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModel()

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

        setupInputValidation()

        if (isSuccessFromForgotPass == true) {
            LoginSnackBar.make(
                binding.root,
                message = requireActivity().getString(R.string.we_have_emailed_your_password_reset_link)
            ).show()
        }

        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())

        binding.buttonSignIn.setOnClickListener {
            viewModel.login(
                binding.eTextEmailAddress.text.toString(),
                binding.eTextPassword.text.toString()
            )
            if(binding.eTextPassword.isCorrectText() && binding.eTextPassword.isCorrectText()) {

            }
        }

        binding.forgotPassword.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(false)
            findNavController().navigate(action)
        }

        viewModel.loginState.observe(viewLifecycleOwner, Observer { loginState ->
            when (loginState) {
                LoginState.SUCCESS -> {
                    println("Login was successful.")
                    findNavController().navigate(R.id.action_login_to_dashboard_fragment)
                }

                LoginState.ERROR -> {
                    println("Login failed.")
                    LoginSnackBar.make(
                        binding.root,
                        message = "Nie poprawne dane logowania"
                    ).show()
                }

                LoginState.LOADING -> {
                    println("Login is in progress.")
                }
            }
        })
        navigationListeners()
    }

    private fun navigationListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonSignIn.setOnClickListener {
            viewModel.login(
                binding.eTextEmailAddress.text.toString(),
                binding.eTextPassword.text.toString()
            )
        }

        binding.forgotPassword.setOnClickListener {
            val action =
                LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment(false)
            findNavController().navigate(action)
        }

        binding.signUpNow.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupInputValidation() {

        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d(ContentValues.TAG, "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.buttonSignIn.isEnabled = isValidInput()
                Log.d(ContentValues.TAG, "onTextChanged")
            }
            override fun afterTextChanged(s: Editable) {
                Log.d(ContentValues.TAG, "afterTextChanged")
            }
        }

        binding.eTextEmailAddress.addTextChangedListener(textWatcher)
        binding.eTextPassword.addTextChangedListener(textWatcher)

        binding.buttonSignIn.isEnabled = false

    }


    private fun isValidInput(): Boolean {
        val email = binding.eTextEmailAddress.text.toString().trim()
        val password = binding.eTextPassword.text.toString().trim()

        return email.isNotEmpty() && password.isNotEmpty()
    }
}
