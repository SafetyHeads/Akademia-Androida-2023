package com.safetyheads.akademiaandroida.presentation.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentSignUpBinding
import com.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.FullNameValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.PasswordValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.isCorrectText
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInputValidation()

        lifecycleScope.launchWhenStarted {
            viewModel.registrationState.collect { result ->
                when {
                    result == null -> {
                        //no-op
                    }

                    result.isSuccess -> {
                        binding.progressBar.isVisible = false
                    }

                    result.isFailure -> {
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
        navigationListeners()
    }

    private fun setupInputValidation() {

        FullNameValidator.attach(binding.eTextFullName, requireContext())
        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())
        PasswordValidator.attach(binding.eTextConfirmPassword, requireContext())

        binding.eTextFullName.addTextChangedListener {
            binding.buttonSignUp.isEnabled = isValidInput()
        }
        binding.eTextEmailAddress.addTextChangedListener {
            binding.buttonSignUp.isEnabled = isValidInput()
        }
        binding.eTextPassword.addTextChangedListener {
            binding.buttonSignUp.isEnabled = isValidInput()
        }
        binding.eTextConfirmPassword.addTextChangedListener {
            binding.buttonSignUp.isEnabled = isValidInput()
        }

        binding.buttonSignUp.isEnabled = false
    }
    private fun isValidInput(): Boolean = binding.eTextFullName.isCorrectText() &&
            binding.eTextEmailAddress.isCorrectText() &&
            binding.eTextPassword.isCorrectText() &&
            binding.eTextConfirmPassword.isCorrectText() &&
            binding.eTextPassword.text.toString() == binding.eTextConfirmPassword.text.toString()

    private fun navigationListeners() {

        binding.buttonSignUp.setOnClickListener {
            binding.progressBar.isVisible = true
            viewModel.signUp(
                binding.eTextFullName.text.toString(),
                binding.eTextEmailAddress.text.toString(),
                binding.eTextPassword.text.toString(),
            )
        }

        binding.buttonBack.customButtonListener {
            findNavController().navigateUp()
        }

        binding.signInNow.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

