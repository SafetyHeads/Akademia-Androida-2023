package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqTab
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqType
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.faq.Faq
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FaqBottomSheetFragmentBinding
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentFaqBinding
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.TextExpandableListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqFragment : Fragment() {
    private lateinit var binding: FragmentFaqBinding
    private val faqViewModel: FaqViewModel by viewModel();

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
    }

    private fun onClick(faq: Faq) {
        if (faq.answer.publish) {
            val bottomSheet = FaqBottomSheetFragment.newInstance(
                faqQuestion = faq.question.text,
                faqAnswer = faq.answer.text
            )
            bottomSheet.show(parentFragmentManager, "FaqBottomSheetDialog")
        }


    }
}