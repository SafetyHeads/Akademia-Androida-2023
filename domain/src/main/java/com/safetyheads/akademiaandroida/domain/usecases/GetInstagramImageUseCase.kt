package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetInstagramImageUseCase(private val repository: ImageRepository) :
    ParameterlessBaseUseCase<List<Media>> {

    override suspend fun invoke(): Flow<Result<List<Media>>> {
        return flow {
            try {
                repository.getInstagramImages().collect { images ->
                    emit(images)
                }
            } catch (error: Exception) {
                emit(Result.failure(error))
            }

        }
    }
}