package com.safetyheads.akademiaandroida.presentation.ui.fragments.login

import android.os.Bundle
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

        if (isSuccessFromForgotPass == true) {
            LoginSnackBar.make(
                binding.root,
                message = requireActivity().getString(R.string.we_have_emailed_your_password_reset_link)
            ).show()
        }

        binding.buttonSignIn.setOnClickListener {
            viewModel.login(
                binding.eTextEmailAddress.text.toString(),
                binding.eTextPassword.text.toString()
            )
        }

        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())

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
}
