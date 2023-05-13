package com.safetyheads.akademiaandroida.presentation.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentChangeProfileAvatarBinding
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ChangeAvatarFragment : Fragment() {

    private lateinit var binding: FragmentChangeProfileAvatarBinding
    private val viewModel: ProfileViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeProfileAvatarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeUI()
        viewModel.tempLogin()
    }

    private fun observeUI() {
        viewModel.userInformation.observe(viewLifecycleOwner) { profile ->
            if (profile.image.isNotEmpty()) {
                Glide.with(requireContext())
                    .load(profile.image)
                    .into(binding.avatarImageView)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { show ->
            binding.progressBar.isVisible = show
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Change avatar error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUI() {
        binding.changePhotoButton.setOnClickListener{
            val changeAvatarBottomSheet = ChangeAvatarBottomSheetDialogFragment()
            changeAvatarBottomSheet.show(childFragmentManager, "ChangeAvatarBottomSheetDialogFragment")
        }

        binding.closeIcon.setOnClickListener {
            findNavController().navigate(R.id.action_changeAvatarFragment_to_profileFragment)
        }
    }

}