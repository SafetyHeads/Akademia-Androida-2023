package com.safetyheads.akademiaandroida

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.ActivitiesList.ListActivity
import com.safetyheads.akademiaandroida.contact_with_us.ContactWithUsFragment
import com.safetyheads.akademiaandroida.databinding.ActivityMainBinding
import com.safetyheads.akademiaandroida.font.FontSylesFragment
import com.safetyheads.akademiaandroida.fragments.WeAreHiringFragment
import com.safetyheads.akademiaandroida.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val splashScreenViewModel: SplashScreenViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            splashScreenViewModel.delay.isActive
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.previevFont.setOnClickListener {
            openFragment(FontSylesFragment())
            binding.previevFont.isVisible = false
            binding.contactWithUs.isVisible = false
            binding.weAreHiring.isVisible = false
        }

        binding.weAreHiring.setOnClickListener {
            openFragment(WeAreHiringFragment())
            binding.previevFont.isVisible = false
            binding.contactWithUs.isVisible = false
            binding.weAreHiring.isVisible = false
        }

        binding.contactWithUs.setOnClickListener {
            openFragment(ContactWithUsFragment())
            binding.previevFont.isVisible = false
            binding.contactWithUs.isVisible = false
            binding.weAreHiring.isVisible = false
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
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }

}