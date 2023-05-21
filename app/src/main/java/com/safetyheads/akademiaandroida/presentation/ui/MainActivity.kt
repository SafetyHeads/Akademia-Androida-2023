package com.safetyheads.akademiaandroida.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.safetyheads.akademiaandroida.databinding.ActivityMainBinding
import com.safetyheads.akademiaandroida.presentation.ui.activities.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.presentation.ui.activitieslist.ListActivity
import com.safetyheads.akademiaandroida.presentation.ui.components.Footer
import com.safetyheads.akademiaandroida.presentation.ui.fragments.contact_with_us.ContactUsFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.font_style.FontSylesFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.map.MapFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.we_are_hiring.WeAreHiringFragment
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
}
