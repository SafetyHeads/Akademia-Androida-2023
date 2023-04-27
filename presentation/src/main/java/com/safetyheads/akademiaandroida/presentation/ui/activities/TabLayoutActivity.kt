package com.safetyheads.akademiaandroida.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.safetyheads.akademiaandroida.databinding.ActivityTabLayoutBinding

class TabLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> binding.textView.text = tab.text
                    1 -> binding.textView.text = tab.text
                    2 -> binding.textView.text = tab.text
                }
            }


            override fun onTabUnselected(tab: TabLayout.Tab) {
                //no-op
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
}
