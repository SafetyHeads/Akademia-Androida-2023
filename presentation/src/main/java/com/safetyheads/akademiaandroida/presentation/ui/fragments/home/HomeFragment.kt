package com.safetyheads.akademiaandroida.presentation.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentHomeBinding
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.DashboardViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        binding.testBtn.setOnClickListener {
            val navHostFragment = requireActivity().supportFragmentManager
                .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_dashboardFragment_to_fontSylesFragment)
        }
    }

    private fun initObservers() {
        viewModel.doesExistUser.observe(viewLifecycleOwner) { isUserLogged ->
            if (isUserLogged) {
                binding.backButton.visibility = View.GONE
            } else {
                binding.backButton.customButtonListener {
                    val intent = Intent()
                    intent.setClassName(
                        requireContext(),
                        "com.safetyheads.akademiaandroida.presentation.ui.MainActivity"
                    )
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }
}