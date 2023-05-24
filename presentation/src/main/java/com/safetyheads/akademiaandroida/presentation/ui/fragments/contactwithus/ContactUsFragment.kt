package com.safetyheads.akademiaandroida.presentation.ui.fragments.contactwithus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentContactUsBinding


class ContactUsFragment : Fragment() {

    private lateinit var binding: FragmentContactUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.textViewEmail.setOnClickListener {
            openEmailApp(it)
        }

        binding.textViewPhone.setOnClickListener {
            openPhoneApp(it)
        }
    }

    private fun openEmailApp(view: View) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:" + (view as TextView).text.toString())
        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    private fun openPhoneApp(view: View) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + (view as TextView).text)
        startActivity(intent)
    }
}
