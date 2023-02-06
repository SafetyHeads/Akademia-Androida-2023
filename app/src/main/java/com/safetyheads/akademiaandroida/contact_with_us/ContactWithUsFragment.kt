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

    private val TAG = "ContactWithUsFragment" 

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
            currentlyOpen = "Facebook"
            if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.facebookPackage) != null)
                startActivity(openActivity())
            else
                openDialog()
        }

        binding.linkedinFill.setOnClickListener {
            currentlyOpen = "Linkedin"
            if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.linkedinPackage) != null)
                startActivity(openActivity())
            else
                openDialog()

        }

        binding.instagramFill.setOnClickListener {
            currentlyOpen = "Instagram"
            if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.instagramPackage) != null)
                startActivity(openActivity())
            else
                openDialog()

        }

        binding.youtubeFill.setOnClickListener {
            currentlyOpen = "YouTube"
            if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.youTubePackage) != null)
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
                        "Facebook" -> data =
                            Uri.parse(ContactWithUsObject.facebookGooglePlay)

                        "Linkedin" -> data =
                            Uri.parse(ContactWithUsObject.linkedinGooglePlay)

                        "Instagram" -> data =
                            Uri.parse(ContactWithUsObject.instagramGooglePlay)

                        "YouTube" -> data =
                            Uri.parse(ContactWithUsObject.youTubeGooglePlay)
                    }
                    setPackage(ContactWithUsObject.googlePlayPackage)
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
                "Facebook" -> {
                    if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.facebookPackage) != null)
                        if((requireContext().packageManager.getPackageInfo(ContactWithUsObject.facebookPackage, 0).versionCode) >= ContactWithUsObject.facebookVersionApp)
                            data = Uri.parse(ContactWithUsObject.facebookAppNewSH + ContactWithUsObject.facebookSH)
                        else
                            data = Uri.parse(ContactWithUsObject.facebookAppOldSH)
                    else
                        data = Uri.parse(ContactWithUsObject.facebookSH)
                }

                "Linkedin" -> {
                    data = Uri.parse(ContactWithUsObject.linkedinSH)
                    if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.linkedinPackage) != null)
                        setPackage(ContactWithUsObject.linkedinPackage)
                }

                "Instagram" -> {
                    data = Uri.parse(ContactWithUsObject.instagramSH)
                    if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.instagramPackage) != null)
                        setPackage(ContactWithUsObject.instagramPackage)
                }

                "YouTube" -> {
                    data = Uri.parse(ContactWithUsObject.youTubeSH)
                    if (requireContext().packageManager.getLaunchIntentForPackage(ContactWithUsObject.youTubePackage) != null)
                        setPackage(ContactWithUsObject.youTubePackage)
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