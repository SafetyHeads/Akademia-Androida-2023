package com.safetyheads.akademiaandroida

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.safetyheads.akademiaandroida.databinding.ActivityDevelopmentMainBinding
import com.safetyheads.akademiaandroida.presentation.ui.MainActivity
import com.safetyheads.akademiaandroida.presentation.ui.activities.splashscreen.SplashScreenViewModel
import com.safetyheads.akademiaandroida.presentation.ui.activitieslist.ListActivity
import com.safetyheads.akademiaandroida.presentation.ui.components.Footer
import com.safetyheads.akademiaandroida.presentation.ui.fragments.contact.ContactFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.fontstyle.FontSylesFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.map.MapFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.wearehiring.WeAreHiringFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DevelopmentMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDevelopmentMainBinding
    private val splashScreenViewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        setupFirebase()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            splashScreenViewModel.getConfig.isActive
        }
        observeConfigChanges()

        super.onCreate(savedInstanceState)
        binding = ActivityDevelopmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupBindings()
        if (!areNotificationsEnabled()) {
            AlertDialog.Builder(this)
                .setTitle("Włącz powiadomienia")
                .setMessage("Aby korzystać z wszystkich funkcji aplikacji, włącz powiadomienia.")
                .setPositiveButton("Ustawienia powiadomień") { _, _ ->
                    openNotificationSettings()
                }
                //  .setNegativeButton("Anuluj", null)
                .show()
        }

        setupOnClickListeners()


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

        binding.startApp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupBindings() {
        binding = ActivityDevelopmentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupFirebase() {
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("all")
    }

    private fun setupOnClickListeners() {
        binding.previevFont.setOnClickListener {
            openFragment(FontSylesFragment())
            hideButtons()
        }

        binding.weAreHiring.setOnClickListener {
            openFragment(WeAreHiringFragment())
            hideButtons()
        }

        binding.contactWithUs.setOnClickListener {
            openFragment(ContactFragment())
            hideButtons()
        }

        binding.mapButton.setOnClickListener {
            openFragment(MapFragment())
            hideButtons()
        }
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

    private fun areNotificationsEnabled(): Boolean {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }

    private fun openNotificationSettings() {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
        } else
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS")
        intent.putExtra("app_package", packageName)
        intent.putExtra("app_uid", applicationInfo.uid)
        startActivity(intent)
    }
}
