package com.safetyheads.akademiaandroida.YouTube.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.YouTube.adapters.VideoAdapter
import com.safetyheads.data.network.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.YouTube.viewModel.VideoViewModel
import com.safetyheads.akademiaandroida.databinding.FragmentVideoBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class YouTubeVideoFragment: Fragment(), VideoAdapter.ClickListener {

    private val TAG = "YouTubeVideoFragment"

    private lateinit var binding: FragmentVideoBinding
    private val videoViewModel: VideoViewModel by activityViewModel()

    private var videoList: ArrayList<com.safetyheads.data.network.entities.video.YouTubeVideoDataClass> = ArrayList()
    private val videoAdapter = VideoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initList()
        initObserver()
    }

    private fun initObserver() {
        videoViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        videoViewModel.videosList.observe(viewLifecycleOwner) {
            videoList = it
            videoAdapter.setData(videoList)
        }

        videoViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Loading video error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initList() {
        binding.rvVideo.adapter = videoAdapter
        binding.rvVideo.layoutManager = LinearLayoutManager(requireContext())
        videoAdapter.setClickListener(this)
    }

    private fun initUI() {
        binding.progressBar.isVisible = false
        binding.addVideo.setOnClickListener {
            if (!binding.progressBar.isVisible)
                videoViewModel.getVideo()
        }
    }

    override fun onClick() {
        videoViewModel.getVideo()
    }
}