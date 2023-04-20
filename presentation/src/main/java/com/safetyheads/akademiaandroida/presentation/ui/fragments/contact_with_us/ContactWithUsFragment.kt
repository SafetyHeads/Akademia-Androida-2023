package com.safetyheads.akademiaandroida.presentation.ui.fragments.contact_with_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.databinding.FragmentContactWithUsBinding

class ContactWithUsFragment : Fragment() {

    private val TAG = "ContactWithUsFragment"

    private lateinit var binding: FragmentContactWithUsBinding
    private lateinit var currentlyOpen: SocialEnumClass
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
            currentlyOpen = SocialEnumClass.Facebook
            socialOpen()
        }

        binding.linkedinFill.setOnClickListener {
            currentlyOpen = SocialEnumClass.Linkedin
            socialOpen()
        }

        binding.instagramFill.setOnClickListener {
            currentlyOpen = SocialEnumClass.Instagram
            socialOpen()
        }

        binding.youtubeFill.setOnClickListener {
            currentlyOpen = SocialEnumClass.YouTube
            socialOpen()
        }
    }

    private fun openCallback(): ContactWithUsCallback {
        val callback = object : ContactWithUsCallback {
            override fun setPositiveButton() {
                val googlePlayToIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(currentlyOpen.downloadGooglePlay)
                    setPackage(SocialEnumClass.GooglePlay.applicationPackage)
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
        val openAplicationIntent = Intent(Intent.ACTION_VIEW).apply {
            when (currentlyOpen) {
                SocialEnumClass.Facebook -> {
                    if (requireContext().packageManager.getLaunchIntentForPackage(currentlyOpen.applicationPackage) != null)
                        if ((requireContext().packageManager.getPackageInfo(
                                currentlyOpen.applicationPackage,
                                0
                            ).versionCode) >= ContactWithUsObject.facebookVersionApp
                        )
                            data =
                                Uri.parse(ContactWithUsObject.facebookAppNewSH + currentlyOpen.socialName)
                        else
                            data = Uri.parse(ContactWithUsObject.facebookAppOldSH)
                    else
                        data = Uri.parse(currentlyOpen.socialName)
                }

                else -> {
                    data = Uri.parse(currentlyOpen.socialName)
                    if (requireContext().packageManager.getLaunchIntentForPackage(currentlyOpen.applicationPackage) != null)
                        setPackage(currentlyOpen.applicationPackage)
                }
            }
        }
        return openAplicationIntent
    }

    private fun socialOpen() {
        if (requireContext().packageManager.getLaunchIntentForPackage(currentlyOpen.applicationPackage) != null)
            startActivity(openActivity())
        else if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.googlePlayPackage) != null)
            openDialog()
        else
            startActivity(openActivity())
    }

    private fun openDialog() {
        val dialog = ContactWithUsDialogFragment(openCallback(), currentlyOpen.name)
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