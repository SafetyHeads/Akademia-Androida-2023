package com.safetyheads.akademiaandroida.career

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.safetyheads.domain.entities.JobOffer
import com.safetyheads.domain.usecases.GetJobOfferUseCase

class CareerViewModel(
    private val getJobOffer: GetJobOfferUseCase
    ): ViewModel() {

    private val _jobOffersList: MutableLiveData<List<JobOffer>> = MutableLiveData()
    var jobOffersList: LiveData<List<JobOffer>> = _jobOffersList

//    fun getJobOffersList() = getJobOffer.invoke()
}