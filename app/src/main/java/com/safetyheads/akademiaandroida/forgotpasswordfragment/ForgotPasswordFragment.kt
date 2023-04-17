package com.safetyheads.akademiaandroida.forgotpasswordfragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.FragmentForgotPasswordBinding
import com.safetyheads.akademiaandroida.forgotpasswordfragment.snackbar.LoginSnackBar
import com.safetyheads.akademiaandroida.utils.EmailValidator
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val forgotPasswordViewModel: ForgotPasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val etEmail = binding.eTextEmailAddress
        val requestBtn = binding.requestButton

        EmailValidator.attach(etEmail)

        etEmail.addTextChangedListener {
            forgotPasswordViewModel.setError(etEmail.error?.toString().orEmpty())
        }

        forgotPasswordViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            if (errorMsg.isNullOrEmpty()) {
                requestBtn.backgroundTintList = getColor(R.color.p_60)
            } else {
                requestBtn.backgroundTintList = getColor(R.color.p_30)
            }
        }
        forgotPasswordViewModel.isSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val bundle = Bundle().apply {
                    putBoolean("isSuccess", isSuccess)
                }
                findNavController()
                    .navigate(R.id.action_forgot_password_fragment_to_login_fragment, bundle)
            } else {
                LoginSnackBar.make(
                    binding.root,
                    message = requireActivity().getString(R.string.failed_sent_forgot_password)
                ).show()
            }
        }

        requestBtn.setOnClickListener {
            if (forgotPasswordViewModel.error.value.isNullOrEmpty()) {
                forgotPasswordViewModel.resetPassword(etEmail.text.toString())
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getColor(color: Int) = ColorStateList.valueOf(
        ContextCompat.getColor(requireActivity(), color)
    )

}