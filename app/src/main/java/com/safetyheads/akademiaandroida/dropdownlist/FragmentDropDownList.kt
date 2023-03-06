package com.safetyheads.akademiaandroida.dropdownlist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ExpandableListView.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.R

class FragmentDropDownList : Fragment() {
    private var expandableListView: ExpandableListView? = null
    private val parentItem = LinkedHashMap<String, ParentModel>()
    private val itemList = ArrayList<ParentModel>()
    var expandableListAdapter: ExpandableListAdapter? = null
    var imageView: ImageView? = null
    var textView: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context?.let { MyClass.setContext(it) };
        val childAt = container!!.getChildAt(0)
        val imageView = childAt.findViewById<ImageView>(R.id.arrow)
        val textView = childAt.findViewById<TextView>(R.id.dropdown_list_title)
        return inflater.inflate(R.layout.fragment_dropdown_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        expandableListView = view.findViewById<View>(R.id.expendableListView) as ExpandableListView
        expandableListAdapter = HelperAdapter(requireContext(), itemList)
        expandableListView!!.setAdapter(expandableListAdapter)
        expandableListView!!.setOnGroupClickListener { parent, v, parentPosition, id ->
            val parentModel = itemList[parentPosition]
            if (parent.isGroupExpanded(parentPosition)) {
                imageView!!.rotation = 180f
                textView!!.setTextColor(Color.parseColor("@color/p_60"))
            } else {
                textView!!.setTextColor(Color.parseColor("@color/s_60"))
                imageView!!.rotation = 0f
            }
            false
        }
        expandableListView!!.setOnGroupExpandListener { }

        // Expandable Listview Group Collapsed listener
        expandableListView!!.setOnGroupCollapseListener {
            // TODO GroupCollapseListener work
        }

        // Expandable Listview on child click listener
        expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id -> false }
    }


    abstract class MyClass {

        companion object {

            @SuppressLint("StaticFieldLeak")
            private lateinit var context: Context

            fun setContext(con: Context) {
                context=con
            }
        }
    }
}