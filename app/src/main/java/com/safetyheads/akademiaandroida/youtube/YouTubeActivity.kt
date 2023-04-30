package com.safetyheads.akademiaandroida.youtube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.youtube.fragments.YouTubeChanelFragment
import com.safetyheads.akademiaandroida.youtube.fragments.YouTubePlayListItemsFragment
import com.safetyheads.akademiaandroida.youtube.fragments.YouTubePlayListsFragment
import com.safetyheads.akademiaandroida.youtube.fragments.YouTubeVideoFragment
import com.safetyheads.akademiaandroida.databinding.ActivityYoutubeBinding


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