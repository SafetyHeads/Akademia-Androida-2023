package com.safetyheads.akademiaandroida.presentation.ui.career

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.akademiaandroida.domain.entities.JobOffer
import com.safetyheads.akademiaandroida.domain.entities.Settings
import com.safetyheads.akademiaandroida.domain.repositories.SettingsRepository
import com.safetyheads.akademiaandroida.domain.usecases.GetJobOfferUseCase
import kotlinx.coroutines.launch

class CareerViewModel(
    private val getJobOffer: GetJobOfferUseCase,
    private val settingsRepository: SettingsRepository,
    ): ViewModel() {

    private val _jobOffersList: MutableLiveData<List<JobOffer>> = MutableLiveData()
    var jobOffersList: LiveData<List<JobOffer>> = _jobOffersList

    private val _failureText: MutableLiveData<String> = MutableLiveData()
    var failureText: LiveData<String> = _failureText

    fun readSetting() = settingsRepository.readSetting(Settings.SEND_NOTIFICATIONS_CAREER)

    fun writeSetting(value: Boolean) = settingsRepository.writeSetting(Settings.SEND_NOTIFICATIONS_CAREER, value)

    fun getJobOffersList() {
    viewModelScope.launch {
        getJobOffer.invoke().collect { result ->
            if(result.isSuccess)
                _jobOffersList.postValue(result.getOrNull())
            else
                _failureText.postValue(result.exceptionOrNull()?.message)

            }
        }
    }
}
