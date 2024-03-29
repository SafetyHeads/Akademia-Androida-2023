package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.JobOffer
import com.safetyheads.akademiaandroida.domain.repositories.CareerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetJobOfferUseCase(private val repository: CareerRepository): ParameterlessBaseUseCase<List<JobOffer>> {

    override suspend fun invoke(): Flow<Result<List<JobOffer>>> {
        return flow {
            try {
                repository.getJobOffersList().collect{ jobOffers ->
                    emit(Result.success(jobOffers))
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
