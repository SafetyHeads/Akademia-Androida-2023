package com.safetyheads.akademiaandroida.presentation.ui.fragments.technologystack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
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
            setExpandableListViewHeight(this, -1)
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

                setExpandableListViewHeight(parent, groupPosition)

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

    //taken from https://gist.github.com/moltak/10458814
    private fun setExpandableListViewHeight(listView: ExpandableListView, group: Int) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.EXACTLY
        )
        for (i in 0 until listAdapter.groupCount) {
            val groupItem = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.measuredHeight
            if (listView.isGroupExpanded(i) && i != group || !listView.isGroupExpanded(i) && i == group) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem = listAdapter.getChildView(
                        i, j, false, null,
                        listView
                    )
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.measuredHeight
                }
            }
            totalHeight += (listView.dividerHeight * (listAdapter.getChildrenCount(i) - 1));
        }
        val params = listView.layoutParams
        var height = totalHeight + listView.dividerHeight * (listAdapter.groupCount - 1) + listView.dividerHeight
        if (height < 10) height = 250
        params.height = height
        listView.layoutParams = params
        listView.requestLayout()
    }
}


