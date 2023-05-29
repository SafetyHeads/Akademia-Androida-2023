package com.safetyheads.akademiaandroida.presentation.ui.fragments.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentChangePasswordBinding
import com.safetyheads.akademiaandroida.presentation.ui.components.snackbar.LoginSnackBar
import com.safetyheads.akademiaandroida.presentation.ui.utils.PasswordValidator
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val changePasswordViewModel: ChangePasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val etCurrentPassword = binding.editTextCurrentPassword
        val etNewPassword = binding.editTextNewPassword
        val etConfirmNewPassword = binding.editTextConfirmNewPassword
        val savePassword = binding.savePassword
        val rejectPassword = binding.rejectPassword

        PasswordValidator.attach(etCurrentPassword, requireContext())
        PasswordValidator.attach(etNewPassword, requireContext())
        PasswordValidator.attach(etConfirmNewPassword, requireContext())

        changePasswordViewModel.isSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                LoginSnackBar.make(
                    binding.root,
                    message = requireActivity().getString(R.string.reset_password_success)
                ).show()
            } else {
                LoginSnackBar.make(
                    binding.root,
                    message = requireActivity().getString(R.string.failed_reset_password)
                ).show()
            }
        }

        rejectPassword.setOnClickListener {
            etCurrentPassword.setText("")
            etNewPassword.setText("")
            etConfirmNewPassword.setText("")
        }

        savePassword.setOnClickListener {
            if (etCurrentPassword.error != "" && etNewPassword.error != "" && etConfirmNewPassword.error != "") {
                if (etNewPassword.text.toString() != etConfirmNewPassword.text.toString()) {
                    LoginSnackBar.make(
                        binding.root,
                        message = requireActivity().getString(R.string.password_not_match)
                    ).show()
                } else {
                    changePasswordViewModel.changePassword(etCurrentPassword.text.toString(), etNewPassword.text.toString())
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}