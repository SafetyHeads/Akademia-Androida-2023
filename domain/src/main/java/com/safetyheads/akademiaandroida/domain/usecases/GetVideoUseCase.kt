package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetVideoUseCase(private val videoRepository: com.safetyheads.akademiaandroida.domain.repositories.VideoRepository):
    BaseUseCase<GetVideoUseCase.VideoParam, Video> {

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