package com.safetyheads.akademiaandroida

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.safetyheads.akademiaandroida.databinding.ActivityMainBinding
import com.safetyheads.akademiaandroida.font.FontSylesFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val fragment = FontSylesFragment()
            fragmentTransaction.replace(binding.frameLayout.id, fragment)
            fragmentTransaction.commit()
            binding.button.visibility = View.GONE
        }
        val rootActivityIntent = Intent(this, RootActivity::class.java)
        binding.rootActivityButton.setOnClickListener {
            startActivity(rootActivityIntent)
        }
    }
}