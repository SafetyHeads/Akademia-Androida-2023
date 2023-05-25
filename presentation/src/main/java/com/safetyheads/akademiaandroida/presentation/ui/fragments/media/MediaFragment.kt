package com.safetyheads.akademiaandroida.presentation.ui.fragments.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentMediaBinding
import com.safetyheads.akademiaandroida.presentation.ui.adapters.MediaAdapter
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.MediaViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaFragment : Fragment() {

    private lateinit var binding: FragmentMediaBinding
    private val mediaViewModel: MediaViewModel by viewModel()

    private val videoAdapter = MediaAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeUI()
        initList()
        initNotificationView()
    }

    private fun initList() {
        binding.recyclerView.adapter = videoAdapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun observeUI() {
        mediaViewModel.connectedList.observe(viewLifecycleOwner) { connectedList ->
            if (binding.radioButtonAllMedia.isChecked)
                videoAdapter.setData(connectedList as ArrayList<Media>)
        }

        mediaViewModel.youtubeFilmList.observe(viewLifecycleOwner) { youtubeFilmList ->
            if (binding.radioButtonMovies.isChecked)
                videoAdapter.setData(youtubeFilmList as ArrayList<Media>)
        }

        mediaViewModel.instagramImageList.observe(viewLifecycleOwner) { instagramImageList ->
            if (binding.radioButtonPhoto.isChecked)
                videoAdapter.setData(instagramImageList as ArrayList<Media>)
        }
    }

    private fun initUI() {
        binding.radioButtonAllMedia.setOnClickListener {
            videoAdapter.setData(mediaViewModel.connectedList.value as ArrayList<Media>)
        }

        binding.radioButtonMovies.setOnClickListener {
            videoAdapter.setData(mediaViewModel.youtubeFilmList.value as ArrayList<Media>)
        }

        binding.radioButtonPhoto.setOnClickListener {
            videoAdapter.setData(mediaViewModel.instagramImageList.value as ArrayList<Media>)
        }
    }

    private fun initNotificationView() {
        val shouldSendNotifications = mediaViewModel.readSetting()
        binding.notificationView.setSwitchButton(shouldSendNotifications)
        binding.notificationView.setExpandableContent(!shouldSendNotifications)

        binding.notificationView.switchButtonListener { isChecked ->
            mediaViewModel.writeSetting(isChecked)
        }
    }

}