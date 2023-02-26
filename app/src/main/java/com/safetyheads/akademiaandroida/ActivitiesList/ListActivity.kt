package com.safetyheads.akademiaandroida.ActivitiesList

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.ClientsFragment
import com.safetyheads.akademiaandroida.ExperienceStructureFragment
import com.safetyheads.akademiaandroida.SplashScreenFragment
import com.safetyheads.akademiaandroida.contact_with_us.ContactWithUsFragment
import com.safetyheads.akademiaandroida.databinding.ActivityListBinding
import com.safetyheads.akademiaandroida.font.FontSylesFragment
import com.safetyheads.akademiaandroida.fragments.WeAreHiringFragment

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
    val splashScreenFragment = SplashScreenFragment()

    val dataListFragments = listOf(
        fontSylesFragment, contactWithUsFragment,
        weAreHiringBinding, clientsFragment, experienceStructureFragment, splashScreenFragment
    )
}






