package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.JobOffer
import kotlinx.coroutines.flow.Flow

interface CareerRepository {

    fun getJobOffersList(): Flow<List<JobOffer>>

}