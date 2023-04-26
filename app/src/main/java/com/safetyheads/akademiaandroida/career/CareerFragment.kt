package com.safetyheads.akademiaandroida.career

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.WebViewActivity
import com.safetyheads.akademiaandroida.databinding.FragmentCareerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareerFragment : Fragment() {

    private lateinit var binding: FragmentCareerBinding
    private val viewModel: CareerViewModel by viewModel()
    private val jobOffersListAdapter = JobOffersAdapter(::onButtonClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCareerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = jobOffersListAdapter

        observeJobOffersList()
        initNotificationView()
    }

    private fun observeJobOffersList() {
        viewModel.getJobOffersList()
        viewModel.jobOffersList.observe(viewLifecycleOwner) { jobOffersList ->
            jobOffersListAdapter.setJobOffers(jobOffersList)
        }

        viewModel.failureText.observe(viewLifecycleOwner) { failureText ->
            Toast.makeText(
                requireContext(),
                failureText,
                Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun initNotificationView() {
        val shouldSendNotifications = viewModel.readSetting()
        binding.notificationView.setSwitchButton(shouldSendNotifications)

        binding.notificationView.switchButtonListener {
            viewModel.writeSetting(it)
        }
    }

    private fun onButtonClick(jobUrl: String) {
        val intent = Intent(requireActivity(), WebViewActivity::class.java)
        intent.putExtra("job_url", jobUrl)
        startActivity(intent)

    }
}
