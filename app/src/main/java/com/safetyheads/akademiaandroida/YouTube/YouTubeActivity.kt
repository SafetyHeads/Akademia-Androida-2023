package com.safetyheads.akademiaandroida.YouTube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.YouTube.fragments.YouTubeChanelFragment
import com.safetyheads.akademiaandroida.YouTube.fragments.YouTubeVideoFragment
import com.safetyheads.akademiaandroida.YouTube.viewModel.ChannelViewModel
import com.safetyheads.akademiaandroida.YouTube.viewModel.VideoViewModel
import com.safetyheads.akademiaandroida.databinding.ActivityYoutubeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class YouTubeActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityYoutubeBinding
    private val videoViewModel by viewModel<VideoViewModel>()
    private val channelViewModel by viewModel<ChannelViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        var fragment: Fragment = YouTubeVideoFragment()

        binding.navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.video -> fragment = YouTubeVideoFragment()
                R.id.channel -> fragment = YouTubeChanelFragment()
                else -> false
            }
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, fragment).commit()
            menuItem.isChecked = true
            title = menuItem.title
            true
        }
    }
}