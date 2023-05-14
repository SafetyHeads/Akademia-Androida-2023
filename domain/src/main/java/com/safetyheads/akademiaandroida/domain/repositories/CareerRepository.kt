package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.JobOffer
import kotlinx.coroutines.flow.Flow

interface CareerRepository {

    suspend fun getJobOffersList(): Flow<List<JobOffer>>

}
