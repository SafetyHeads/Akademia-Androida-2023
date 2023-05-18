package com.safetyheads.akademiaandroida.presentation.ui.fragments.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.safetyheads.akademiaandroida.domain.entities.firebasefirestore.Profile
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentProfileInformationBinding
import com.safetyheads.akademiaandroida.presentation.ui.utils.AddressValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.CityValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.FullNameValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.PhoneNumberValidator
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProfileInformationFragment : Fragment() {

    private lateinit var binding: FragmentProfileInformationBinding
    private val viewModel: ProfileViewModel by activityViewModel()

    private lateinit var actualProfile: Profile
    private var fullNameJob: Job? = null
    private var jobPositionJob: Job? = null
    private var phoneNumberJob: Job? = null
    private var streetJob: Job? = null
    private var cityJob: Job? = null
    private var countryJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeUI()
    }

    private fun observeUI() {
        viewModel.userInformation.observe(viewLifecycleOwner) { profileInformation ->
            if (profileInformation != null) {
                actualProfile = profileInformation
                binding.apply {
                    if (fullNameJob == null)
                        fullNameEditText.setText("${profileInformation.firstName} ${profileInformation.lastName}")
                    if (jobPositionJob == null)
                        jobPositionEditText.setText(profileInformation.jobPosition)
                    if (phoneNumberJob == null)
                        phoneNumberEditText.setText(profileInformation.phoneNumber)
                    if (streetJob == null)
                        streetEditText.setText("${profileInformation.address.streetName} ${profileInformation.address.streetNumber}")
                    if (cityJob == null)
                        cityEditText.setText("${profileInformation.address.zipCode} ${profileInformation.address.city}")
                    if (countryJob == null)
                        countryEditText.setText(profileInformation.address.country)
                    emailTextView.text = profileInformation.email
                }
            }
        }
    }

    private fun initUI() {
        FullNameValidator.attach(binding.fullNameEditText, requireContext())
        PhoneNumberValidator.attach(binding.phoneNumberEditText, requireContext())
        AddressValidator.attach(binding.streetEditText, requireContext())
        CityValidator.attach(binding.cityEditText, requireContext())

        binding.apply {
            fullNameEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (("${actualProfile.firstName} ${actualProfile.lastName}") != binding.fullNameEditText.text.toString()) {
                        fullNameJob?.cancel()
                        fullNameJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(5000)
                            changeFullName()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            jobPositionEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (actualProfile.jobPosition != binding.jobPositionEditText.text.toString()) {
                        jobPositionJob?.cancel()
                        jobPositionJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(5000)
                            changeJobPosition()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            phoneNumberEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (actualProfile.phoneNumber != binding.phoneNumberEditText.text.toString()) {
                        phoneNumberJob?.cancel()
                        phoneNumberJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(5000)
                            changePhoneNumber()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            streetEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (("${actualProfile.address.streetName} ${actualProfile.address.streetNumber}") != binding.streetEditText.text.toString()) {
                        streetJob?.cancel()
                        streetJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(5000)
                            changeStreet()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            cityEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (("${actualProfile.address.zipCode} ${actualProfile.address.city}") != binding.cityEditText.text.toString()) {
                        cityJob?.cancel()
                        cityJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(5000)
                            changeCity()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            countryEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (actualProfile.address.country != binding.countryEditText.text.toString()) {
                        countryJob?.cancel()
                        countryJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(5000)
                            changeCountry()
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }

    }

    private fun changeFullName() {
        fullNameJob = null
        if (FullNameValidator.IS_CORRECT) {
            val fullName = binding.fullNameEditText.text.toString()
            val nameParts = fullName.split(" ")
            if (nameParts.size == 2) {
                val firstName = nameParts[0]
                val lastName = nameParts[1]
            }
        }
    }

    private fun changeJobPosition() {
        jobPositionJob = null
        val jobPosition = binding.jobPositionEditText.text.toString()


    }

    private fun changePhoneNumber() {
        phoneNumberJob = null
        if (PhoneNumberValidator.IS_CORRECT) {
            val phoneNumber = binding.phoneNumberEditText.text.toString()
            viewModel.changePhoneNumber(phoneNumber)
        }
    }

    private fun changeCountry() {
        countryJob = null
        val country = binding.countryEditText.text.toString()

    }

    private fun changeStreet() {
        streetJob = null
        if (AddressValidator.IS_CORRECT) {
            val street = binding.streetEditText.text.toString()
            val streetParts = street.split(" ")
            if (streetParts.size == 2) {
                val streetName = streetParts[0]
                val streetNumber = streetParts[1]
            }
        }
    }

    private fun changeCity() {
        cityJob = null
        if (CityValidator.IS_CORRECT) {
            val city = binding.cityEditText.text.toString()
            val cityParts = city.split(" ")
            if (cityParts.size == 2) {
                val zipCode = cityParts[0]
                val city = cityParts[1]
            }
        }
    }

}