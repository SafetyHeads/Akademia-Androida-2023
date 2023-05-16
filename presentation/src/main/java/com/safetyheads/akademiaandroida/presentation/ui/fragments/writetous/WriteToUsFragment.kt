package com.safetyheads.akademiaandroida.writetous

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentWriteToUsBinding
import com.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.FullNameValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.PhoneNumberValidator


class WriteToUsFragment : Fragment() {

    private lateinit var binding: FragmentWriteToUsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWriteToUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        FullNameValidator.attach(binding.eTextFullName, requireContext())
        PhoneNumberValidator.attach(binding.eTextPhoneNumber, requireContext())
        EmailValidator.attach(binding.eTextEmailAddress)

    }
}
