package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.domain.repositories.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllVideoUseCase(private val videoRepository: VideoRepository) :
    ParameterlessBaseUseCase<List<Media>> {

    override suspend fun invoke(): Flow<Result<List<Media>>> {
        return flow {
            try {
                videoRepository.getAllVideos().collect { video ->
                    emit(video)
                }
            } catch (error: Exception) {
                emit(Result.failure(error))
            }
        }
    }
}