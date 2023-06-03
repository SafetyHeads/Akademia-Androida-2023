package com.safetyheads.akademiaandroida.presentation.ui.fragments.writetous

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentWriteToUsBottomSheetBinding
import com.safetyheads.akademiaandroida.presentation.ui.utils.EmailValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.FullNameValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.PhoneNumberValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.isCorrectText

class WriteToUsBottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        const val WRITE_TO_US_SHEET_DIALOG = "WriteToUsBottomSheetFragment"
    }

    private lateinit var binding: FragmentWriteToUsBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWriteToUsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val behavior = BottomSheetBehavior.from(binding.constraintContainer)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
        FullNameValidator.attach(binding.eTextFullName, requireContext())
        PhoneNumberValidator.attach(binding.eTextPhoneNumber, requireContext())
        EmailValidator.attach(binding.eTextEmailAddress)

        binding.eTextFullName.addTextChangedListener {
            buttonIsEnabled()
        }
        binding.eTextPhoneNumber.addTextChangedListener {
            buttonIsEnabled()
        }
        binding.eTextEmailAddress.addTextChangedListener {
            buttonIsEnabled()
        }
    }

    private fun buttonIsEnabled() {
        binding.buttonSendYourMessage.isEnabled = binding.eTextFullName.isCorrectText() &&
                binding.eTextPhoneNumber.isCorrectText() &&
                binding.eTextEmailAddress.isCorrectText()
    }
}
