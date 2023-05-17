package com.safetyheads.akademiaandroida.presentation.ui.fragments.youtubefragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentChannelBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class YouTubeChanelFragment : Fragment() {

    private lateinit var binding: FragmentChannelBinding
    private val channelViewModel: ChannelViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChannelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
    }

    private fun initObserver() {
        channelViewModel.isLoading.observe(viewLifecycleOwner) { show ->
            binding.progressBar.isVisible = show
        }

        channelViewModel.channelInfoInformation.observe(viewLifecycleOwner) { channelInformation ->
            initChannel(channelInformation)
        }

        channelViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Loading channel error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUI() {
        binding.progressBar.isVisible = false
    }

    private fun initChannel(channelInformation: com.safetyheads.akademiaandroida.domain.entities.Channel) {
            binding.tvChannelName.text = channelInformation.channelName
            binding.tvChannelDescription.text = channelInformation.channelDescription
            Glide.with(requireContext()).load(channelInformation.channelBannerUrl)
                .into(binding.imageChannel)
            Glide.with(requireContext()).load(channelInformation.channelAvatarUrl)
                .into(binding.imageLogo)
    }
}