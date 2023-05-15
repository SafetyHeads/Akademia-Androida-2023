package com.safetyheads.akademiaandroida.presentation.ui.fragments.faq

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqTab
import com.safetyheads.akademiaandroida.domain.entities.faqs.FaqType
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentFaqBinding
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.TextExpandableListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FaqFragment : Fragment() {
    private lateinit var binding: FragmentFaqBinding
    private lateinit var expandableListAdapter: TextExpandableListAdapter
    private val faqViewModel : FaqViewModel by viewModel();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFaqBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        faqViewModel.getFaqs()

        faqViewModel.faqsList.observe(viewLifecycleOwner) { faqs ->
            if (faqs != null) {
                for (faq in faqs) {
                    if(faq.type == FaqType.Benefits.tag) {

                    } else if(faq.type == FaqType.Delegations.tag) {

                    } else {

                    }
                }
            }
        }

        binding.faqTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position) {
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
}