package com.safetyheads.akademiaandroida


import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.ActivitiesList.ListActivity
import com.safetyheads.akademiaandroida.contactusform.ContactUsFragment
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
            splashScreenViewModel.getConfig.isActive
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
            //openFragment(ContactWithUsFragment())
            openFragment(ContactUsFragment())
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

        //testing firebase.crashlytics
        val crashButton = Button(this)
        crashButton.text = "Test Crash"
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }
        addContentView(
            crashButton, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        //create instance Remote Config to firebase and download data
//        val remoteConfig = FirebaseRemoteConfig.getInstance()
//        val configSettings = FirebaseRemoteConfigSettings.Builder()
//            .setFetchTimeoutInSeconds(60)
//            .build()
//        remoteConfig.setConfigSettingsAsync(configSettings)
//        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
//
//        val versionCode = remoteConfig.getString("versionCode")
//        val apiUrl = remoteConfig.getString("apiUrl")
//
//        remoteConfig.fetchAndActivate()
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val updated = task.result
//                    Log.d(TAG, "Config params updated: $updated")
//
//                    Toast.makeText(
//                        this,
//                        "versionCode:" + versionCode +
//                                "apiUrl:" + apiUrl,
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                } else {
//                    Toast.makeText(
//                        this, "Fetch failed",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }
}