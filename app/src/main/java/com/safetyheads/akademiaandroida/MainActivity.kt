package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.safetyheads.akademiaandroida.contact_with_us.ContactWithUsFragment
import com.safetyheads.akademiaandroida.databinding.ActivityMainBinding
import com.safetyheads.akademiaandroida.font.FontSylesFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.previevFont.setOnClickListener {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val fragment = FontSylesFragment()
            fragmentTransaction.replace(binding.frameLayout.id, fragment)
            fragmentTransaction.commit()
            binding.previevFont.visibility = View.GONE
            binding.hiringFragment.visibility = View.GONE
        }

        binding.hiringFragment.setOnClickListener {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val fragment = ContactWithUsFragment()
            fragmentTransaction.replace(binding.frameLayout.id, fragment)
            fragmentTransaction.commit()
            binding.previevFont.visibility = View.GONE
            binding.hiringFragment.visibility = View.GONE
        }
    }
}