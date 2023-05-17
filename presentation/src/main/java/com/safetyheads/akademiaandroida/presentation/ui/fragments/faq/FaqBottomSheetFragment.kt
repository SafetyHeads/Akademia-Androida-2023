package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentBottomSheetFaqBinding

class FaqBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetFaqBinding
    companion object {
        const val FAQ_SHEET_DIALOG = "FaqBottomSheetDialog"
        private const val FAQ_QUESTION = "FaqQuestion"
        private const val FAQ_ANSWER = "FaqAnswer"
        fun newInstance(faqQuestion: String, faqAnswer : String): FaqBottomSheetFragment {
            val fragment = FaqBottomSheetFragment()
            val args = Bundle().apply {
                putString(FAQ_QUESTION, faqQuestion)
                putString(FAQ_ANSWER, faqAnswer)
            }
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetFaqBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.parent as View).setBackgroundColor(Color.TRANSPARENT)
        binding.answerTextView.text = arguments?.getString(FAQ_ANSWER)
        binding.questionTextView.text = arguments?.getString(FAQ_QUESTION)
        binding.btnOk.setOnClickListener {
            dismiss()
        }
    }
}