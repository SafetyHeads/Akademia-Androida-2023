package com.safetyheads.akademiaandroida.dropdownlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.safetyheads.akademiaandroida.R

class HelperAdapter(private val context: Context, private val childList: ArrayList<ParentModel>) :
    BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return childList.size
    }

    override fun getChildrenCount(parentPosition: Int): Int {
        val itemList = childList[parentPosition].getItemList()
        return itemList!!.size
    }

    override fun getGroup(parentPosition: Int): Any {
        return childList[parentPosition]
    }

    override fun getChild(parentPosition: Int, childPosition: Int): Any {
        val itemList = childList[parentPosition].getItemList()
        return itemList!![childPosition]
    }

    override fun getGroupId(parentPosition: Int): Long {
        return parentPosition.toLong()
    }

    override fun getChildId(parentPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        parentPosition: Int,
        isExpanded: Boolean,
        view: View,
        parent: ViewGroup
    ): View {
        var view = view
        val parentInfo = getGroup(parentPosition) as ParentModel
        if (view == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.dropdown_list_parent_item, null)
        }
        val textView = view.findViewById<View>(R.id.dropdown_list_title) as TextView
        textView.text = parentInfo.getName()!!.trim()
        return view
    }

    override fun getChildView(
        parentPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        view: View,
        parent: ViewGroup
    ): View {
        var view = view
        val childInfo = getChild(parentPosition, childPosition) as ChildModel
        if (view == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.dropdown_list_child_item, null)
        }
        val textView = view.findViewById<View>(R.id.itemList) as TextView
        textView.text = childInfo.getName()
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }
}
