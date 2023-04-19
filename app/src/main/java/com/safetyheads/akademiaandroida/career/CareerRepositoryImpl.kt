package com.safetyheads.akademiaandroida.career

import com.safetyheads.domain.entities.JobOffer
import com.safetyheads.domain.repositories.CareerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CareerRepositoryImpl: CareerRepository {

    val jobOffersList = listOf(
        JobOffer(1,
            "Frontend Developer",
            "(Angular, Azure, JS/TS)",
            "Regular / Senior",
            "B2B: 80 – 160 zł"
        ),
        JobOffer(2,
            "Frontend Developer",
            "(Angular, Azure, JS/TS)",
            "Regular / Senior",
            "B2B: 80 – 160 zł"
        )
    )

    override fun getJobOffersList(): Flow<List<JobOffer>>  = flow {
        emit(jobOffersList)
    }
}
