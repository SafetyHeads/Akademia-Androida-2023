package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.JobOffer
import com.safetyheads.domain.repositories.CareerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetJobOfferUseCase(private val repository: CareerRepository): BaseUseCase<GetJobOfferUseCase.JobOfferParam, List<JobOffer>> {

    class JobOfferParam(val jobOfferId: Int) : BaseUseCase.Params

    override suspend fun invoke(parameter: JobOfferParam): Flow<Result<List<JobOffer>>> {
        return flow {
            try {
                repository.getJobOffersList().collect{ jobOffers ->
                    emit(Result.success(jobOffers))
                }
            } catch (error: Exception) {
                emit(Result.failure(error))
            }
        }
    }
}