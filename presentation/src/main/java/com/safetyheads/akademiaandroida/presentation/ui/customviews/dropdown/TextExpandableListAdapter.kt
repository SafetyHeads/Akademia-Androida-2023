package com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.safetyheads.akademiaandroida.domain.entities.technologystack.ParentModel
import com.safetyheads.akademiaandroida.presentation.R

class TextExpandableListAdapter(
    private val context: Context,
    list: List<ParentModel> = emptyList()
) : BaseExpandableListAdapter() {

    private val parentModels: MutableList<ParentModel> = list.toMutableList()
    fun updateList(newList: List<ParentModel>) {
        parentModels.clear()
        parentModels.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getGroupView(
        parentPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = LayoutInflater.from(context).inflate(R.layout.dropdown_list_parent_item, parent,false)
        val listTitle = getGroup(parentPosition)
        val listTitleTextView = view.findViewById<TextView>(R.id.dropdown_list_title)
        listTitleTextView.text = listTitle.name
        return view
    }

    override fun getChildView(
        parentPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = LayoutInflater.from(context).inflate(R.layout.dropdown_list_child_item, parent,false)
        val expandedListText = getChild(parentPosition, childPosition)
        val expandedListTextView = view.findViewById<TextView>(R.id.itemList)
        expandedListTextView.text = expandedListText.name
        return view
    }

    override fun getGroupCount() = parentModels.size

    override fun getChildrenCount(parentPosition: Int) = parentModels[parentPosition].itemList.size

    override fun getGroup(parentPosition: Int) = parentModels[parentPosition]

    override fun getChild(parentPosition: Int, childPosition: Int) =
        parentModels[parentPosition].itemList[childPosition]

    override fun getGroupId(parentPosition: Int) = parentPosition.toLong()

    override fun getChildId(parentPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun hasStableIds() = true

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = false
}
