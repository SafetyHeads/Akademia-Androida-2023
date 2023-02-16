package com.safetyheads.akademiaandroida

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.safetyheads.akademiaandroida.contact_with_us.ContactWithUsFragment
import com.safetyheads.akademiaandroida.databinding.ActivityMainBinding
import com.safetyheads.akademiaandroida.font.FontSylesFragment
import com.safetyheads.akademiaandroida.fragments.WeAreHiringFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.previev_font.setOnClickListener {
            openFragment(FontSylesFragment())
            binding.previev_font.isVisible = false
            binding.contact_with_us.isVisible = false
            binding.weAreHiring.isVisible = false
        }

        binding.weAreHiring.setOnClickListener {
            openFragment(WeAreHiringFragment())
            binding.previev_font.isVisible = false
            binding.contact_with_us.isVisible = false
            binding.weAreHiring.isVisible = false
            
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