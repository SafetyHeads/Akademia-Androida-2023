package com.safetyheads.data.network.repository

import com.safetyheads.data.network.mapper.VideoMapper
import com.safetyheads.data.network.service.YouTubeService
import com.safetyheads.akademiaandroida.domain.entities.Video
import com.safetyheads.akademiaandroida.domain.repositories.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VideoRepositoryImpl(
    private val youTubeService: YouTubeService,
    private val videoMapper: VideoMapper,
    private val apiKey: String
) : VideoRepository {

    override suspend fun getVideo(previousFilmDate: String): Flow<Result<Video>> =
        flow {
            val retrofitYouTubeVideo =
                youTubeService
                    .getVideo(
                        apiKey,
                        YouTubeApiConsts.YOUTUBE_CHANNEL_ID,
                        YouTubeApiConsts.YOUTUBE_API_PART_VIDEO,
                        YouTubeApiConsts.YOUTUBE_API_ORDER,
                        YouTubeApiConsts.YOUTUBE_API_VIDEO_MAX_RESULTS,
                        previousFilmDate
                    )
            val video: Video = videoMapper.transform(retrofitYouTubeVideo)
            emit(Result.success(video))
        }.catch { exception ->
            emit(Result.failure(exception))
        }

}