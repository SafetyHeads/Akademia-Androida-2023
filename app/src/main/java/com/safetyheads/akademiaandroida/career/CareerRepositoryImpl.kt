package com.safetyheads.akademiaandroida.career

import com.safetyheads.domain.entities.JobOffer
import com.safetyheads.domain.repositories.CareerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CareerRepositoryImpl: CareerRepository {

    private val jobOffersList = arrayListOf(
        JobOffer(1,
            "Frontend Developer",
            "(Angular, Azure, JS/TS)",
            "Regular / Senior",
            "B2B: 80 – 160 zł",
            "https://safetyheads.com/job-offer/fd/"
        ),
        JobOffer(2,
            "FullStack JavaScript Engineer",
            "(React/Node/RN/AWS)",
            "Regular / Senior",
            "B2B: 130 – 170 zł",
            "https://safetyheads.com/job-offer/fs/"
        )
    )

    override suspend fun getJobOffersList(): Flow<List<JobOffer>> = flow {
        emit(jobOffersList)
    }
}
