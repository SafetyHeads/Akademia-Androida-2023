package com.safetyheads.akademiaandroida.dropdownlist.dropdown

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.safetyheads.akademiaandroida.R
import com.safetyheads.domain.entities.technologystack.ParentModel

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
        val parentInfo = getGroup(parentPosition)

        return convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.dropdown_list_parent_item, null).apply {
                findViewById<TextView>(R.id.dropdown_list_title).text = parentInfo.name
            }
    }

    override fun getChildView(
        parentPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val childInfo = getChild(parentPosition, childPosition)

        return convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.dropdown_list_child_item, null).apply {
                findViewById<TextView>(R.id.itemList).text = childInfo.name
            }
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
