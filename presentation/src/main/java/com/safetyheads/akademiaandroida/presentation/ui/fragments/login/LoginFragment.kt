package com.safetyheads.akademiaandroida.presentation.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentLoginBinding
import com.safetyheads.akademiaandroida.presentation.ui.activities.DashboardActivity
import com.safetyheads.akademiaandroida.presentation.ui.components.snackbar.LoginSnackBar
import com.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator
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

        viewModel.loginState.observe(viewLifecycleOwner) { loginState ->
            when (loginState) {
                LoginState.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    println("Login was successful.")
                    val intent = Intent(requireActivity(), DashboardActivity::class.java)
                    startActivity(intent)
                }

                LoginState.ERROR -> {
                    binding.progressBar.isVisible = false
                    println("Login failed.")
                    LoginSnackBar.make(
                        binding.root,
                        message = "Nie poprawne dane logowania"
                    ).show()
                }

                LoginState.LOADING -> {
                    println("Login is in progress.")
                }

                else -> {
                    //no-op
                }
            }
        }
        navigationListeners()
    }

    private fun navigationListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonSignIn.setOnClickListener {
            binding.progressBar.isVisible = true
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
        //PasswordValidator.attach(binding.eTextPassword, requireContext())

        binding.eTextPassword.addTextChangedListener {
            binding.buttonSignIn.isEnabled = isValidInput()
        }

        binding.eTextEmailAddress.addTextChangedListener {
            binding.buttonSignIn.isEnabled = isValidInput()
        }

        binding.buttonSignIn.isEnabled = false

    }

    private fun isValidInput(): Boolean = binding.eTextEmailAddress.isCorrectText()
            && binding.eTextPassword.isCorrectText()
}
