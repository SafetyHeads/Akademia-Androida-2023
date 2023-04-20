package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.Video
import com.safetyheads.domain.repositories.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetVideoUseCase(private val repository: VideoRepository): BaseUseCase<GetVideoUseCase.VideoParam,Video> {

    class VideoParam(val videoId: String): BaseUseCase.Params

    override suspend fun invoke(parameter: VideoParam): Flow<Result<Video>> {
        return flow {
            try {
                repository.getVideo(parameter.videoId).collect { video ->
                    emit(Result.success(video))
                }
            } catch (error: IllegalStateException) {
                emit(Result.failure(error))
            }
        }
    }
}
