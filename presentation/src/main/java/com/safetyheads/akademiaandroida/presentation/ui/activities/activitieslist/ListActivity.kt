package com.safetyheads.akademiaandroida.ActivitiesList

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.databinding.ActivityListBinding
import com.safetyheads.akademiaandroida.login.LoginFragment
import com.safetyheads.akademiaandroida.presentation.ui.activities.activitieslist.ActivitiesAdapter
import com.safetyheads.akademiaandroida.presentation.ui.activities.activitieslist.FragmentsAdapter
import com.safetyheads.akademiaandroida.presentation.ui.customviews.ExperienceStructureFragment
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.FragmentDropDownList
import com.safetyheads.akademiaandroida.presentation.ui.fragments.clients.ClientsFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.contact_with_us.ContactWithUsFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.font_style.FontSylesFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.forgotpasswordfragment.ForgotPasswordFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.we_are_hiring.WeAreHiringFragment

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
    val forgotPasswordFragment = ForgotPasswordFragment()
    val loginFragment = LoginFragment()

    val dataListFragments = listOf(
        fontSylesFragment,
        contactWithUsFragment,
        weAreHiringBinding,
        clientsFragment,
        experienceStructureFragment,
        fragmentDropDownList,
        loginFragment,
        forgotPasswordFragment
    )
}





