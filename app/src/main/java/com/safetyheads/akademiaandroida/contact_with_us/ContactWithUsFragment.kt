package com.safetyheads.akademiaandroida.contact_with_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.databinding.FragmentContactWithUsBinding

class ContactWithUsFragment : Fragment() {
    companion object {
        val TAG = "ContactWithUsFragment"

        val Facebook = "Facebook"
        val Instagram = "Instagram"
        val Linkedin = "Linkedin"
        val YouTube = "YouTube"

        val FacebookPackage = "com.facebook.katana"
        val InstagramPackage = "com.instagram.android"
        val YouTubePackage = "com.google.android.youtube"
        val LinkedinPackage = "com.linkedin.android"
        val GooglePlayPackage = "com.android.vending"

        val FacebookGooglePlay = "https://play.google.com/store/apps/details?id=com.facebook.katana"
        val LinkedinGooglePlay = "https://play.google.com/store/apps/details?id=com.linkedin.android"
        val InstagramGooglePlay = "https://play.google.com/store/apps/details?id=com.instagram.android"
        val YouTubeGooglePlay = "https://play.google.com/store/apps/details?id=com.google.android.youtube"

        val FacebookSH = "https://www.facebook.com/SafetyHeads"
        val InstagramSH = "https://www.instagram.com/safety_heads/"
        val LinkedinSH = "https://www.linkedin.com/company/safetyheads"
        val YouTubeSH = "https://www.youtube.com/@safetyheads8094"

        val FacebookAppNewSH = "fb://facewebmodal/f?href="
        val FacebookAppOldSH = "fb://page/2927034007521523"
        val FacebookVersionApp = 3002850
    }

    private lateinit var binding: FragmentContactWithUsBinding
    private lateinit var currentlyOpen: String
    private var goToInstal = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactWithUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.facebookFill.setOnClickListener {
            currentlyOpen = Facebook
            if (requireContext().packageManager.getLaunchIntentForPackage(FacebookPackage) != null)
                startActivity(openActivity())
            else
                openDialog()
        }

        binding.linkedinFill.setOnClickListener {
            currentlyOpen = Linkedin
            if (requireContext().packageManager.getLaunchIntentForPackage(LinkedinPackage) != null)
                startActivity(openActivity())
            else
                openDialog()

        }

        binding.instagramFill.setOnClickListener {
            currentlyOpen = Instagram
            if (requireContext().packageManager.getLaunchIntentForPackage(InstagramPackage) != null)
                startActivity(openActivity())
            else
                openDialog()

        }

        binding.youtubeFill.setOnClickListener {
            currentlyOpen = YouTube
            if (requireContext().packageManager.getLaunchIntentForPackage(YouTubePackage) != null)
                startActivity(openActivity())
            else
                openDialog()

        }
    }

    private fun openCallback(): ContactWithUsCallback {
        val callback = object : ContactWithUsCallback {
            override fun setPositiveButton() {
                val googlePlayToIntent = Intent(Intent.ACTION_VIEW).apply {
                    when (currentlyOpen) {
                        Facebook -> data =
                            Uri.parse(FacebookGooglePlay)

                        Linkedin -> data =
                            Uri.parse(LinkedinGooglePlay)

                        Instagram -> data =
                            Uri.parse(InstagramGooglePlay)

                        YouTube -> data =
                            Uri.parse(YouTubeGooglePlay)
                    }
                    setPackage(GooglePlayPackage)
                }
                goToInstal = true
                startActivity(googlePlayToIntent)
            }

            override fun setNegativeButton() {
                startActivity(openActivity())
            }
        }
        return callback
    }

    private fun openActivity(): Intent {
        var Intent = Intent(Intent.ACTION_VIEW).apply {
            when (currentlyOpen) {
                Facebook -> {
                    if (requireContext().packageManager.getLaunchIntentForPackage(FacebookPackage) != null)
                        if((requireContext().packageManager.getPackageInfo(FacebookPackage, 0).versionCode) >= FacebookVersionApp)
                            data = Uri.parse(FacebookAppNewSH + FacebookSH)
                        else
                            data = Uri.parse(FacebookAppOldSH)
                    else
                        data = Uri.parse(FacebookSH)
                }

                Linkedin -> {
                    data = Uri.parse(LinkedinSH)
                    if (requireContext().packageManager.getLaunchIntentForPackage(LinkedinPackage) != null)
                        setPackage(LinkedinPackage)
                }

                Instagram -> {
                    data = Uri.parse(InstagramSH)
                    if (requireContext().packageManager.getLaunchIntentForPackage(InstagramPackage) != null)
                        setPackage(InstagramPackage)
                }

                YouTube -> {
                    data = Uri.parse(YouTubeSH)
                    if (requireContext().packageManager.getLaunchIntentForPackage(YouTubePackage) != null)
                        setPackage(YouTubePackage)
                }
            }
        }
        return Intent
    }

    private fun openDialog() {
        val dialog = ContactWithUsDialogFragment(openCallback(), currentlyOpen)
        dialog.show(parentFragmentManager, TAG)
    }

    override fun onResume() {
        super.onResume()
        if (goToInstal) {
            goToInstal = false
            startActivity(openActivity())
        }
    }
}