package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.Config
import com.safetyheads.domain.repositories.ConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetConfigUseCase(private val repository: ConfigRepository):
    ParameterlessBaseUseCase<Config> {

    override suspend fun invoke(): Flow<Result<Config>> {

        return flow {
            try {
                repository.getConfig().collect { config ->
                    emit(Result.success(config))
                }
            } catch (error: Exception) {
                emit(
                    Result.failure(error)
                )
            }
        }
    }
}