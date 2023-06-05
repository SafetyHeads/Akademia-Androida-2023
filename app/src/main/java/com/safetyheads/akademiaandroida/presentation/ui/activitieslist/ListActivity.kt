package com.safetyheads.akademiaandroida.presentation.ui.activitieslist


import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.databinding.ActivityListBinding
import com.safetyheads.akademiaandroida.presentation.ui.career.CareerFragment
import com.safetyheads.akademiaandroida.presentation.ui.customviews.ExperienceStructureFragment
import com.safetyheads.akademiaandroida.presentation.ui.customviews.dropdown.FragmentDropDownList
import com.safetyheads.akademiaandroida.presentation.ui.fragments.clients.ClientsFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.contactwithus.ContactWithUsFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.faq.FaqFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.fontstyle.FontSylesFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.forgotpasswordfragment.ForgotPasswordFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.login.LoginFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.map.MapFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.technologystack.TechnologyStackFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.wearehiring.WeAreHiringFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.YouTubeChanelFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.YouTubeVideoFragment
import com.safetyheads.akademiaandroida.presentation.ui.signup.SignUpFragment
import com.safetyheads.akademiaandroida.presentation.ui.utils.getPackageInfoCompat


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
        val packageInfo =
            packageManager.getPackageInfoCompat(packageName, PackageManager.GET_ACTIVITIES)
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
    val careerFragment = CareerFragment()
    val technolgyStackFragment = TechnologyStackFragment()
    val signUpFragment = SignUpFragment()
    val mapFragment = MapFragment()
    val youTubeVideoFragment = YouTubeVideoFragment()
    val youTubeChanelFragment = YouTubeChanelFragment()
    val faqFragment = FaqFragment()

    val dataListFragments = listOf(
        fontSylesFragment,
        contactWithUsFragment,
        weAreHiringBinding,
        clientsFragment,
        experienceStructureFragment,
        fragmentDropDownList,
        loginFragment,
        forgotPasswordFragment,
        technolgyStackFragment,
        signUpFragment,
        mapFragment,
        youTubeVideoFragment,
        youTubeChanelFragment,
        careerFragment,
        faqFragment
    )
}






