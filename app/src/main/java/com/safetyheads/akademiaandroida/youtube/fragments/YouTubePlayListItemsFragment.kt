package com.safetyheads.akademiaandroida.youtube.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.youtube.adapters.VideoAdapter
import com.safetyheads.akademiaandroida.youtube.viewModel.PlayListViewModel
import com.safetyheads.akademiaandroida.databinding.FragmentPlaylistItemsBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class YouTubePlayListItemsFragment: Fragment() {

    private lateinit var binding: FragmentPlaylistItemsBinding
    private val playListViewModel: PlayListViewModel by activityViewModel()

    private val videoAdapter = VideoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initList()
        initObserver()
    }

    private fun initObserver() {
        playListViewModel.isLoadingPlayListItems.observe(viewLifecycleOwner) { show ->
            binding.progressBar.isVisible = show
        }

        playListViewModel.listPlayListItems.observe(viewLifecycleOwner) { playListItems ->
            videoAdapter.setData(playListItems)
        }

        playListViewModel.errorMessagePlayListItems.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Loading playlist item error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initList() {
        binding.rvPlaylistItems.adapter = videoAdapter
        binding.rvPlaylistItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initUI() {
        videoAdapter.clearAll()
        playListViewModel.cleanPlayListItems()
        binding.progressBar.isVisible = false
    }
}