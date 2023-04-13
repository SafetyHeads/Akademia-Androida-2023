package com.safetyheads.akademiaandroida.YouTube.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.YouTube.adapters.PlayListItemsAdapter
import com.safetyheads.akademiaandroida.YouTube.viewModel.PlayListViewModel
import com.safetyheads.akademiaandroida.databinding.FragmentPlaylistItemsBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class YouTubePlayListItemsFragment: Fragment() {

    private val TAG = "YouTubePlayListItemsFragment"

    private lateinit var binding: FragmentPlaylistItemsBinding
    private val playListViewModel: PlayListViewModel by activityViewModel()

    private val adapter = PlayListItemsAdapter()

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
        playListViewModel.isLoadingPlayListItems.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        playListViewModel.listPlayListItems.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setData(it)
            }
        }

        playListViewModel.errorMessagePlayListItems.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Loading playlist item error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initList() {
        binding.rvPlaylistItems.adapter = adapter
        binding.rvPlaylistItems.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initUI() {
        adapter.clearAll()
        playListViewModel.cleanPlayListItems()
        binding.progressBar.isVisible = false
    }
}