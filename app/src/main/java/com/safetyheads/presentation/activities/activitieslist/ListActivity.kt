package com.safetyheads.presentation.activities.activitieslist

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.databinding.ActivityListBinding
import com.safetyheads.presentation.customviews.ExperienceStructureFragment
import com.safetyheads.presentation.customviews.dropdown.FragmentDropDownList
import com.safetyheads.presentation.fragments.ClientsFragment
import com.safetyheads.presentation.fragments.contact_with_us.ContactWithUsFragment
import com.safetyheads.presentation.fragments.font_style.FontSylesFragment
import com.safetyheads.presentation.fragments.we_are_hiring.WeAreHiringFragment

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ActivitiesAdapter(createListActivity(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView.adapter = adapter

        val adapterFragment = FragmentsAdapter(dataListFragments, this)
        binding.recyclerView2.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerView2.adapter = adapterFragment
    }

    private fun createListActivity(): List<String> = buildList {
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        val activities = packageInfo.activities
        val activityList = ArrayList<String>()

        if (activities != null) {
            for (activityInfo in activities) {
                activityList.add(activityInfo.name)
            }
        }

        for (i in activities.indices) {
            val newActivities = activityList[i]
            add(newActivities)

        }
    }

    val fontSylesFragment = FontSylesFragment()
    val contactWithUsFragment = ContactWithUsFragment()
    val weAreHiringBinding = WeAreHiringFragment()
    val clientsFragment = ClientsFragment()
    val experienceStructureFragment = ExperienceStructureFragment()
    val fragmentDropDownList = FragmentDropDownList()

    val dataListFragments = listOf(
        fontSylesFragment,
        contactWithUsFragment,
        weAreHiringBinding,
        clientsFragment,
        experienceStructureFragment,
        fragmentDropDownList
    )
}