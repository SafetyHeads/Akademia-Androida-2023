package com.safetyheads.akademiaandroida.presentation.ui.fragments.launchsreen

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.safetyheads.akademiaandroida.presentation.R
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentLaunchScreenBinding
import com.safetyheads.akademiaandroida.presentation.ui.activities.DashboardActivity
import com.safetyheads.akademiaandroida.presentation.ui.components.Footer
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.LaunchScreenViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LaunchScreenFragment : Fragment() {

    private lateinit var binding: FragmentLaunchScreenBinding
    private val viewModel: LaunchScreenViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val welcomeMessage = binding.txtWelcome
        val welcomeMessageTxT = welcomeMessage.text

        val shColor = ContextCompat.getColor(requireContext(), R.color.p_60)
        val sfStart =
            welcomeMessageTxT.indexOf(resources.getString(R.string.launch_screen_txt_company_name))
        val sfEnd = sfStart + resources.getString(R.string.launch_screen_txt_company_name).length

        val spannable: Spannable = SpannableString(welcomeMessage.text)
        spannable.setSpan(
            ForegroundColorSpan(shColor),
            sfStart,
            sfEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        welcomeMessage.setText(spannable, TextView.BufferType.SPANNABLE)

        childFragmentManager.beginTransaction()
            .add(R.id.parent_layout, Footer())
            .commit()

        navigationListeners()
        initObserver()
    }

    private fun initObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner) { loginResult ->
            if (loginResult) {
                startActivity(Intent(requireContext(), DashboardActivity::class.java))
                requireActivity().finish()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null)
                Toast.makeText(
                    context,
                    getString(R.string.error_connecting_to_the_server),
                    Toast.LENGTH_SHORT
                ).show()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun navigationListeners() {
        binding.txtSign.setOnClickListener {
            val action =
                LaunchScreenFragmentDirections.actionLaunchScreenFragmentToLoginFragment()
            findNavController().navigate(action)

        }

        binding.button.setOnClickListener {
            viewModel.anonymousLogin()
        }
    }
}

