package com.safetyheads.akademiaandroida.career

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safetyheads.domain.entities.JobOffer
import com.safetyheads.domain.entities.Settings
import com.safetyheads.domain.repositories.SettingsRepository
import com.safetyheads.domain.usecases.GetJobOfferUseCase
import kotlinx.coroutines.launch

class CareerViewModel(
    private val getJobOffer: GetJobOfferUseCase,
    private val settingsRepository: SettingsRepository
    ): ViewModel() {

    private val _jobOffersList: MutableLiveData<ArrayList<JobOffer>> = MutableLiveData()
    var jobOffersList: LiveData<ArrayList<JobOffer>> = _jobOffersList

    private val _failureText: MutableLiveData<String> = MutableLiveData()
    var failureText: LiveData<String> = _failureText

    fun readSetting(settings: Settings) = settingsRepository.readSetting(settings)

    fun writeSetting(settings: Settings, value: Boolean) = settingsRepository.writeSetting(settings, value)

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