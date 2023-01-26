package com.safetyheads.akademiaandroida

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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
        setContentView(R.layout.activity_main)
        // testing Footer
        supportFragmentManager.beginTransaction()
            .add(R.id.footer_container, Footer())
            .commit()
    }
}