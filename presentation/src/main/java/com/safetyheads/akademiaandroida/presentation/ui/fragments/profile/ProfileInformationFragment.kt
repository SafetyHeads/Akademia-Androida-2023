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
import com.safetyheads.akademiaandroida.presentation.ui.utils.CityValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.FullNameValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.PhoneNumberValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.StreetNameValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.StreetNumberValidator
import com.safetyheads.akademiaandroida.presentation.ui.utils.ZipCodeValidator
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
    private var streetNameJob: Job? = null
    private var streetNumberJob: Job? = null
    private var zipCodeJob: Job? = null
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
                    if (streetNameJob == null)
                        streetNameEditText.setText(profileInformation.address.streetName)
                    if (streetNumberJob == null)
                        streetNumberEditText.setText(profileInformation.address.streetNumber)
                    if (cityJob == null)
                        cityEditText.setText(profileInformation.address.city)
                    if (zipCodeJob == null)
                        zipcodeEditText.setText(profileInformation.address.zipCode)
                    if (countryJob == null)
                        countryEditText.setText(profileInformation.address.country)
                    emailTextView.text = profileInformation.email
                }
            }
        }

        viewModel.logOutProfile.observe(viewLifecycleOwner) { logout ->
            if (logout == true) {
                viewModel.resetProfileEntities()
                //navigate to activity for unregistered
            }
        }

        viewModel.deleteProfile.observe(viewLifecycleOwner) { delete ->
            if (delete == true) {
                viewModel.resetProfileEntities()
                //navigate to activity for unregistered
            }
        }
    }

    private fun initUI() {
        FullNameValidator.attach(binding.fullNameEditText, requireContext())
        PhoneNumberValidator.attach(binding.phoneNumberEditText, requireContext())

        StreetNameValidator.attach(binding.streetNameEditText, requireContext())
        StreetNumberValidator.attach(binding.streetNumberEditText, requireContext())

        ZipCodeValidator.attach(binding.zipcodeEditText, requireContext())
        CityValidator.attach(binding.cityEditText, requireContext())

        binding.apply {
            deleteAccount.setOnClickListener {
                viewModel.deleteProfile()
            }

            logOutAccount.setOnClickListener {
                viewModel.logOutProfile()
            }

            arrowBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

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
                        fullNameJob = resetJob { changeFullName() }
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
                        jobPositionJob = resetJob { changeJobPosition() }
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
                        phoneNumberJob = resetJob { changePhoneNumber() }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            streetNameEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (actualProfile.address.streetName != binding.streetNameEditText.text.toString()) {
                        streetNameJob?.cancel()
                        streetNameJob = resetJob { changeStreetName() }
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
                    if (actualProfile.address.city != binding.cityEditText.text.toString()) {
                        cityJob?.cancel()
                        cityJob = resetJob { changeCity() }
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
                        countryJob = resetJob { changeCountry() }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            streetNumberEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (actualProfile.address.streetNumber != binding.streetNumberEditText.text.toString()) {
                        countryJob?.cancel()
                        countryJob = resetJob { changeStreetNumber() }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            zipcodeEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (actualProfile.address.zipCode != binding.zipcodeEditText.text.toString()) {
                        countryJob?.cancel()
                        countryJob = resetJob { changeZipCode() }
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
                viewModel.changeName(firstName, lastName)
            }
        }
    }

    private fun changeJobPosition() {
        jobPositionJob = null
        val jobPosition = binding.jobPositionEditText.text.toString()
        viewModel.changeJobPosition(jobPosition)
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
        viewModel.changeCountryAddress(country)
    }

    private fun changeStreetName() {
        streetNameJob = null
        if (StreetNameValidator.IS_CORRECT) {
            val street = binding.streetNameEditText.text.toString()
            viewModel.changeStreetAddress(street)
        }
    }


    private fun changeCity() {
        cityJob = null
        if (CityValidator.IS_CORRECT) {
            val city = binding.cityEditText.text.toString()
            viewModel.changeCityAddress(city)
        }
    }

    private fun changeZipCode() {
        zipCodeJob = null
        if (ZipCodeValidator.IS_CORRECT) {
            val zipCode = binding.zipcodeEditText.text.toString()
            viewModel.changeZipCode(zipCode)
        }
    }

    private fun changeStreetNumber() {
        streetNumberJob = null
        if (StreetNumberValidator.IS_CORRECT) {
            val streetNumber = binding.streetNumberEditText.text.toString()
            viewModel.changeStreetNumber(streetNumber)
        }
    }

    private fun resetJob(function: () -> Unit): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            function.invoke()
        }
    }

}