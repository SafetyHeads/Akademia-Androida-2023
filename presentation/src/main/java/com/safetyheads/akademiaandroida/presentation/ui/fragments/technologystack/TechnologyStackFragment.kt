package com.safetyheads.akademiaandroida.presentation.ui.fragments.technologystack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentTechnologyStackBinding
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.TextExpandableListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TechnologyStackFragment : Fragment() {

    private lateinit var binding: FragmentTechnologyStackBinding
    private lateinit var expandableListAdapter: TextExpandableListAdapter
    private val technologyStackViewModel: TechnologyStackViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expandableListAdapter = TextExpandableListAdapter(requireContext())

        lifecycleScope.launch {
            technologyStackViewModel.items.collect { items ->
                expandableListAdapter.updateList(items)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentTechnologyStackBinding.inflate(inflater)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.expendableListView.apply {
            setAdapter(this@TechnologyStackFragment.expandableListAdapter)
            setOnGroupClickListener { parent, view, groupPosition, _ ->
                if (parent.isGroupExpanded(groupPosition)) {
                    view.findViewById<ImageView>(R.id.arrow).rotation = 0f
                    view.findViewById<TextView>(R.id.dropdown_list_title)
                        .setTextColor(context.getColor(R.color.s_60))
                } else {
                    view.findViewById<ImageView>(R.id.arrow).rotation = 180f
                    view.findViewById<TextView>(R.id.dropdown_list_title)
                        .setTextColor(context.getColor(R.color.p_60))
                }
                false


            }
        }

        binding.technologyStackTab.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> technologyStackViewModel.tabSelected(TechnologyStackTab.Mobile)
                    1 -> technologyStackViewModel.tabSelected(TechnologyStackTab.Web)
                    2 -> technologyStackViewModel.tabSelected(TechnologyStackTab.Others)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // no-op
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // no-op
            }
        })
    }
}


