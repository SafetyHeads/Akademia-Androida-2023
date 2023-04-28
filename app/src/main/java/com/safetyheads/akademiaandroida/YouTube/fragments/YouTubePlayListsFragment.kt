package com.safetyheads.akademiaandroida.YouTube.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.YouTube.YouTubeActivity
import com.safetyheads.akademiaandroida.YouTube.adapters.PlaylistAdapter
import com.safetyheads.akademiaandroida.YouTube.viewModel.PlayListViewModel
import com.safetyheads.akademiaandroida.databinding.FragmentPlaylistBinding
import com.safetyheads.akademiaandroida.domain.entities.Playlist
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class YouTubePlayListsFragment: Fragment(), PlaylistAdapter.ClickListener {

    private val TAG = "YouTubePlayListsFragment"

    private lateinit var binding: FragmentPlaylistBinding
    private val playListViewModel: PlayListViewModel by activityViewModel()

    private val playlistAdapter = PlaylistAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initList()
        initObserver()
    }

    private fun initObserver() {
        playListViewModel.isLoadingPlayLists.observe(viewLifecycleOwner) { show ->
            binding.progressBar.isVisible = show
        }

        playListViewModel.listPlayLists.observe(viewLifecycleOwner) { playLists ->
            playlistAdapter.setData(playLists)
        }

        playListViewModel.errorMessagePlayLists.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Loading playlist error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initList() {
        binding.rvPlaylist.adapter = playlistAdapter
        binding.rvPlaylist.layoutManager = LinearLayoutManager(requireContext())
        playlistAdapter.setClickListener(this)
    }

    private fun initUI() {
        binding.progressBar.isVisible = false
    }

    override fun onClick(item: com.safetyheads.akademiaandroida.domain.entities.Playlist, position: Int) {
        playListViewModel.getPlayListItems(item.playlistId)
        val activity = requireActivity() as YouTubeActivity
        activity.goToPlayList()
    }
}