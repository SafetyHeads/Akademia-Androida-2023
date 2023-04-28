package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.firebasefirestore.TechnologyStack
import com.safetyheads.domain.repositories.TechnologyStackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetTechnologyStackUseCase(private val repository: TechnologyStackRepository) :
    ParameterlessBaseUseCase<TechnologyStack> {

    override suspend fun invoke(): Flow<Result<TechnologyStack>> {
        return repository.getTechnologyStack()
            .map { result -> result }
            .catch { exception -> emit(Result.failure(exception)) }
    }
}