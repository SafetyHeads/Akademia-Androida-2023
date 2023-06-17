package com.safetyheads.akademiaandroida.presentation.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentDashboardBinding
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.DashboardViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
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