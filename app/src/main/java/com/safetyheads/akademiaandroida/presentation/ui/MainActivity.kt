package com.safetyheads.akademiaandroida.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.ActivityMainBinding
import com.safetyheads.akademiaandroida.presentation.ui.activities.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.presentation.ui.activitieslist.ListActivity
import com.safetyheads.akademiaandroida.presentation.ui.components.Footer
import com.safetyheads.akademiaandroida.presentation.ui.fragments.contact_with_us.ContactUsFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.font_style.FontSylesFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.we_are_hiring.WeAreHiringFragment
import com.safetyheads.akademiaandroida.presentation.ui.map.MapFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            splashScreenViewModel.getConfig.isActive
        }
        observeConfigChanges()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.previevFont.setOnClickListener {
            openFragment(FontSylesFragment())
            hideButtons()
        }

        binding.weAreHiring.setOnClickListener {
            openFragment(WeAreHiringFragment())
            hideButtons()
        }

        binding.contactWithUs.setOnClickListener {
            //openFragment(ContactWithUsFragment())
            openFragment(ContactUsFragment())
            hideButtons()
        }

        binding.mapButton.setOnClickListener {
            openFragment(MapFragment())
            hideButtons()
        }

        val rootActivityIntent = Intent(this, RootActivity::class.java)
        binding.rootActivityButton.setOnClickListener {
            startActivity(rootActivityIntent)
        }

        val activityListIntent = Intent(this, ListActivity::class.java)
        binding.activityList.setOnClickListener {
            startActivity(activityListIntent)
        }

        // testing Footer
        supportFragmentManager.beginTransaction()
            .add(R.id.footer_container, Footer())
            .commit()

        //testing firebase.crashlytics
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw IllegalStateException("Test Crash") // Force a crash
        }
        addContentView(
            crashButton, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }

    private fun observeConfigChanges() {
        splashScreenViewModel.config.observe(this) { config ->
            Toast.makeText(
                applicationContext,
                "Version Code: ${config.versionCode} \n Api Url: ${config.apiUrl}",
                Toast.LENGTH_LONG
            )
                .show()
        }

        splashScreenViewModel.failureText.observe(this) { failureText ->
            Toast.makeText(
                applicationContext,
                failureText,
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    private fun hideButtons() {
        binding.previevFont.isVisible = false
        binding.contactWithUs.isVisible = false
        binding.weAreHiring.isVisible = false
        binding.mapButton.isVisible = false
    }
}