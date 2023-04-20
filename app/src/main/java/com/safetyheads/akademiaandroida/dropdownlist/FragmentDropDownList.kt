package com.safetyheads.akademiaandroida.dropdownlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.FragmentDropdownListBinding
import com.safetyheads.akademiaandroida.dropdownlist.dropdown.TextExpandableListAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentDropDownList : Fragment() {

    private var binding: FragmentDropdownListBinding? = null
    private lateinit var expandableListAdapter: TextExpandableListAdapter
    private val dropDownListViewModel: DropDownListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expandableListAdapter = TextExpandableListAdapter(requireContext())

        dropDownListViewModel.viewModelScope.launch {
            dropDownListViewModel.items.collect { items ->
                expandableListAdapter.updateList(items)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentDropdownListBinding.inflate(inflater)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.apply {
            expendableListView.apply {
                setAdapter(this@FragmentDropDownList.expandableListAdapter)
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
        }
    }
}
