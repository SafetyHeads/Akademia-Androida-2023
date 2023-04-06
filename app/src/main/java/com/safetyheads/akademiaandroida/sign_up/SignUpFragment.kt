package com.safetyheads.akademiaandroida.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.databinding.FragmentSignUpBinding
import com.safetyheads.akademiaandroida.utils.EmailValidator
import com.safetyheads.akademiaandroida.utils.FullNameValidator
import com.safetyheads.akademiaandroida.utils.PasswordValidator
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

        binding.buttonSignUp.setOnClickListener() {
            viewModel.signUp(binding.eTextFullName, binding.eTextEmailAddress, binding.eTextPassword, binding.eTextConfirmPassword)
        }
        FullNameValidator.attach(binding.eTextFullName, requireContext())
        EmailValidator.attach(binding.eTextEmailAddress)
        PasswordValidator.attach(binding.eTextPassword, requireContext())
        PasswordValidator.attach(binding.eTextConfirmPassword, requireContext())
    }
}
//ProgressBar dorobic powinien sie pojawiac i znikac na podstawie LiveDate

//ScrollView
