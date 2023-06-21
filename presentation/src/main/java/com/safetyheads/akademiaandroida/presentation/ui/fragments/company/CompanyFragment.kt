package com.safetyheads.akademiaandroida.presentation.ui.fragments.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentCompanyBinding

class CompanyFragment : Fragment() {

    private lateinit var binding: FragmentCompanyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.about))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.in_numbers))

        val myAdapter = CompanyPagerAdapter(
            requireActivity().supportFragmentManager,
            requireActivity().lifecycle
        )
        val viewPager = binding.viewPager

        viewPager.adapter = myAdapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //no-op
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //no-op
            }

        })

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }
}
