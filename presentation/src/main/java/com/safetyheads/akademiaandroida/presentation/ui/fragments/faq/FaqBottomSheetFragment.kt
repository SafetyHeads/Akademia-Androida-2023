package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.safetyheads.akademiaandroida.presentation.databinding.FaqBottomSheetFragmentBinding

class FaqBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FaqBottomSheetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FaqBottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.faqBottomSheet.setBackgroundColor(0)
    }
}