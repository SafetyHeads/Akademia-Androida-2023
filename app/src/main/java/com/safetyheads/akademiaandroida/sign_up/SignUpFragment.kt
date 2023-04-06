package com.safetyheads.akademiaandroida.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.databinding.FragmentSignUpBinding
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

        binding.buttonSignUp.setOnClickListener() {

            //przypinanie powinno byc w onViewCreate
            viewModel.signUp(binding.eTextFullName, binding.eTextEmailAddress, binding.eTextPassword, binding.eTextConfirmPassword)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        //Reszte validatorów podpiąć

        //UserReposytory to interface w niej createUser podaje email i haslo

        //ProgressBar dorobic powinien sie pojawiac i znikac na podstawie LiveDate

        //Livedata Regisistration Complete

        //ScrollView


        PasswordValidator.attach(binding.eTextPassword, requireContext())


    }

}
