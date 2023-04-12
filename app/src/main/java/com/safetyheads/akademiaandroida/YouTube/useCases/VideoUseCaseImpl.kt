package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.akademiaandroida.YouTube.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.YouTube.repository.VideoRepository
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VideoUseCaseImpl(private val videoRepository: VideoRepository): VideoUseCase {

    override suspend fun execute(previousFilmDate: String): Flow<NetworkResult<YouTubeVideoDataClass>> {
        return flow {
            emit(NetworkResult.loading())
            try {
                videoRepository.getYouTubeVideo(previousFilmDate).collect { video ->
                    emit(video)
                }
            } catch (error: Exception) {
                emit(NetworkResult.error(error))
            }
        }
    }
}