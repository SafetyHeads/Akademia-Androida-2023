package com.safetyheads.akademiaandroida.YouTube.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.YouTube.entities.channel.ChannelDataClass
import com.safetyheads.akademiaandroida.YouTube.viewModel.ChannelViewModel
import com.safetyheads.akademiaandroida.databinding.FragmentChannelBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class YouTubeChanelFragment : Fragment() {

    private lateinit var binding: FragmentChannelBinding
    private val channelViewModel: ChannelViewModel by activityViewModel()

    private lateinit var channelInformation: ChannelDataClass

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
        channelViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        channelViewModel.channelInformation.observe(viewLifecycleOwner) {
            if (it != null) {
                channelInformation = it
                initChannel()
            }
        }

        channelViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Loading channel error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initUI() {
        binding.progressBar.isVisible = false
    }

    private fun initChannel() {
        channelInformation.items.forEach { channel ->
            binding.tvChannelName.text = channel.snippet.title
            binding.tvChannelDescription.text = channel.snippet.description
            Glide.with(requireContext()).load(channel.brandingSettings.image.bannerExternalUrl)
                .into(binding.imageChannel)
            Glide.with(requireContext()).load(channel.snippet.thumbnails.high.url)
                .into(binding.imageLogo)
        }
    }
}