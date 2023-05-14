package com.safetyheads.akademiaandroida.presentation.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentProfileBottomSheetBinding
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProfileBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentProfileBottomSheetBinding
    private val viewModel: ProfileViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeUI()
    }

    private fun observeUI() {
        viewModel.userInformation.observe(viewLifecycleOwner) { profile ->
            if (profile.image.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(profile.image)
                    .into(binding.avatarImageView)
            }
            if (profile.userName.isNotEmpty()) {
                binding.nicknameTextView.text = getString(R.string.hello_nickname, profile.userName)
            }
        }
    }

    private fun initUI() {
        binding.apply {
            avatarCardView.setOnClickListener { changePhoto() }
            editAvatarCardView.setOnClickListener { changePhoto() }
            profileCardView.setOnClickListener { goToProfile() }
            passwordCardView.setOnClickListener { goToPassword() }
            notificationCardView.setOnClickListener { goToNotifications() }
            locationCardView.setOnClickListener { goToLocalization() }
        }
    }

    private fun goToLocalization() {
    }

    private fun goToNotifications() {
    }

    private fun goToPassword() {
    }

    private fun goToProfile() {
    }

    private fun changePhoto() {
    }

}