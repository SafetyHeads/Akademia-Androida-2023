package com.safetyheads.akademiaandroida.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.ActivityYoutubeBinding
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.YouTubeChanelFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.YouTubePlayListItemsFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.YouTubePlayListsFragment
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube.YouTubeVideoFragment


class YouTubeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYoutubeBinding

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
                R.id.playlist -> fragment = YouTubePlayListsFragment()
                else -> false
            }
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, fragment).commit()
            menuItem.isChecked = true
            title = menuItem.title
            true
        }
    }

    fun goToPlayList() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, YouTubePlayListItemsFragment()).commit()
    }
}