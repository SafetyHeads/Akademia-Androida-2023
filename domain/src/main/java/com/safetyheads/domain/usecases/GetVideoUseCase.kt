package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.Video
import com.safetyheads.domain.repositories.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetVideoUseCase(private val videoRepository: VideoRepository): BaseUseCase<GetVideoUseCase.VideoParam,Video> {

    class VideoParam(val previousFilmDate: String): BaseUseCase.Params

    override suspend fun invoke(parameter: VideoParam): Flow<Result<Video>> {
        return flow {
            try {
                videoRepository.getVideo(parameter.previousFilmDate).collect{ video ->
                    emit(video)
                }
            }   catch (error: Exception) {
                emit(Result.failure(error))
            }
        }
    }
}