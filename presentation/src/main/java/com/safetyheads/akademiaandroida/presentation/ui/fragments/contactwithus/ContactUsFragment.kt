package com.safetyheads.akademiaandroida.presentation.ui.fragments.contactwithus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentContactUsBinding
import com.safetyheads.akademiaandroida.presentation.ui.fragments.writetous.WriteToUsBottomSheetFragment

class ContactUsFragment : Fragment() {
    private lateinit var binding: FragmentContactUsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactUsBinding.inflate(layoutInflater)
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
        binding.buttonFillForm.setOnClickListener {
            val sheet = WriteToUsBottomSheetFragment()

            sheet.show(
                parentFragmentManager,
                WriteToUsBottomSheetFragment.WRITE_TO_US_SHEET_DIALOG
            )
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