package com.safetyheads.akademiaandroida.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.databinding.FragmentCareerBinding
import com.safetyheads.domain.entities.Settings
import org.koin.androidx.viewmodel.ext.android.viewModel

class CareerFragment : Fragment(), OnButtonClickListener {

    private lateinit var binding: FragmentCareerBinding
    private val viewModel: CareerViewModel by viewModel()
    private val jobOffersListAdapter = JobOffersAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCareerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notificationView.initOnClickListeners()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
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
        val notificationsState = viewModel.readSetting(Settings.SEND_NOTIFICATIONS)
        binding.notificationView.setSwitchButton(notificationsState)

        binding.notificationView.switchButtonListener {
            viewModel.writeSetting(Settings.SEND_NOTIFICATIONS, it)
        }
    }

    override fun onButtonClick(jobUrl: String, position: Int) {
        val myWebView = binding.webView
        myWebView.loadUrl(jobUrl)
        binding.notificationView.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.linearlayout.visibility = View.GONE
    }
}