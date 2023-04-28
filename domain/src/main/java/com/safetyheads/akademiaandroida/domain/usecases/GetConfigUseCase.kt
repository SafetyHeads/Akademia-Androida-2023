package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.Config
import com.safetyheads.akademiaandroida.domain.repositories.ConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetConfigUseCase(private val repository: com.safetyheads.akademiaandroida.domain.repositories.ConfigRepository) :
    ParameterlessBaseUseCase<com.safetyheads.akademiaandroida.domain.entities.Config> {

    override suspend fun invoke(): Flow<Result<com.safetyheads.akademiaandroida.domain.entities.Config>> {

        return flow {
            try {
                repository.getConfig().collect { config ->
                    emit(Result.success(config))
                }
            } catch (error: IllegalStateException) {
                emit(
                    Result.failure(error)
                )
            }
        }
    }
}
