package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqTab
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentFaqBinding
import com.safetyheads.akademiaandroida.presentation.ui.components.snackbar.LoginSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqFragment : Fragment() {
    private lateinit var binding: FragmentFaqBinding
    private val faqViewModel: FaqViewModel by viewModel()
    private lateinit var askDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionRV: RecyclerView = binding.questionsRecyclerView
        questionRV.layoutManager = LinearLayoutManager(requireActivity())

        faqViewModel.typedFaqsList.observe(viewLifecycleOwner) { faqs ->
            if (faqs != null)
                questionRV.adapter = FaqAdapter(faqs, ::onClick)
        }

        binding.faqTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> faqViewModel.tabSelected(FaqTab.Benefits)
                    1 -> faqViewModel.tabSelected(FaqTab.Delegations)
                    2 -> faqViewModel.tabSelected(FaqTab.AMA)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // no-op
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //no-op
            }

        })

        binding.askQuestionButton.setOnClickListener { askQuestionDialog() }

        faqViewModel.isSendSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                askDialog.dismiss()
                LoginSnackBar.make(
                    binding.root,
                    message = requireActivity().getString(R.string.success_question_sent_information)
                ).show()
            } else {
                LoginSnackBar.make(
                    binding.root,
                    message = requireActivity().getString(R.string.failed_question_sent_information)
                ).show()
            }
        }
    }

    private fun askQuestionDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_ask_question, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        askDialog = builder.create()
        askDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        askDialog.show()

        val eTextQuestion = dialogView.findViewById<EditText>(R.id.eTextQuestion)
        val btnSend: Button = dialogView.findViewById(R.id.sendButton)

        eTextQuestion.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnSend.setBackgroundResource(R.drawable.button_n60_background)
            } else {
                btnSend.setBackgroundResource(R.drawable.button_p30_background)
            }
        }

        btnSend.setOnClickListener {
            val textQuestion = eTextQuestion.text
            if (textQuestion.isNullOrEmpty().not()) {
                faqViewModel.sendQuestion(textQuestion.toString())
            }
        }
    }

    private fun onClick(faq: Faq) {
        if (faq.answer.publish) {
            val bottomSheet = FaqBottomSheetFragment.newInstance(
                faqQuestion = faq.question.text,
                faqAnswer = faq.answer.text
            )
            bottomSheet.show(parentFragmentManager, FaqBottomSheetFragment.FAQ_SHEET_DIALOG)
        }
    }
}