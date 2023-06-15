package com.safetyheads.akademiaandroida.presentation.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.ToolbarProfileBinding
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProfileToolbar : Fragment() {

    private lateinit var binding: ToolbarProfileBinding
    private val viewModel: ProfileViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ToolbarProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeUI()
        viewModel.tempLogin()
        viewModel.getSessionInfo()
    }

    private fun observeUI() {
        viewModel.doesExistUser.observe(viewLifecycleOwner) { doesExist ->
            if (doesExist)
                binding.avatarCardView.visibility = View.VISIBLE
            else
                binding.avatarCardView.visibility = View.GONE
        }
        viewModel.userInformation.observe(viewLifecycleOwner) { profile ->
            if (profile.image.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(profile.image)
                    .into(binding.avatarImageView)
            }
        }
    }

    private fun initUI() {
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, navDestination, _ ->

            if (navDestination.id == R.id.careerFragment
                || navDestination.id == R.id.mediaFragment
                || navDestination.id == R.id.dashboardFragment
                || navDestination.id == R.id.contactUsFragment
            ) {
                binding.backButton.visibility = View.GONE
            } else {
                binding.backButton.visibility = View.VISIBLE
            }
        }

        binding.backButton.customButtonListener {
            navController.navigateUp()
        }

        binding.avatarCardView.setOnClickListener {
            val modalBottomSheet = ProfileBottomSheetDialogFragment()
            modalBottomSheet.show(childFragmentManager, "ProfileBottomSheetDialogFragment")
        }
    }

}