package com.safetyheads.akademiaandroida.presentation.ui.fragments.forgotpasswordfragment.user_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentUserTestBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserTestFragment : Fragment() {
    private lateinit var binding: FragmentUserTestBinding
    private val userTestViewModel: UserTestViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentUserTestBinding.inflate(inflater)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loginButton.setOnClickListener { userTestViewModel.login() }
        binding.firebaseIdButton.setOnClickListener { userTestViewModel.firebaseIdClicked() }
        binding.showUserDbButton.setOnClickListener { userTestViewModel.showUser() }
        binding.setFirebaseButton.setOnClickListener { userTestViewModel.setFcm() }
        binding.removeFirebaseButton.setOnClickListener { userTestViewModel.clearFcm() }

        userTestViewModel.message.observe(viewLifecycleOwner) {
            binding.message.text = it
        }
    }
}