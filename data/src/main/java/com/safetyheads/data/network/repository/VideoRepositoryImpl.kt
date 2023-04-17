package com.safetyheads.data.network.repository

import com.safetyheads.data.network.mapper.VideoMapper
import com.safetyheads.data.network.`object`.YouTubeApi
import com.safetyheads.data.network.service.YouTubeService
import com.safetyheads.datamodule.BuildConfig
import com.safetyheads.domain.entities.Video
import com.safetyheads.domain.repositories.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VideoRepositoryImpl(
    private val youTubeService: YouTubeService,
    private val videoMapper: VideoMapper
) : VideoRepository {

    override suspend fun getVideo(previousFilmDate: String): Flow<Result<Video>> =
        flow {
            val retrofitYouTubeVideo =
                youTubeService
                    .getVideo(
                        BuildConfig.YOUTUBE_DATA_API_KEY,
                        YouTubeApi.YOUTUBE_CHANNEL_ID,
                        YouTubeApi.YOUTUBE_API_PART_VIDEO,
                        YouTubeApi.YOUTUBE_API_ORDER,
                        YouTubeApi.YOUTUBE_API_VIDEO_MAX_RESULTS,
                        previousFilmDate
                    )
            val video: Video = videoMapper.transform(retrofitYouTubeVideo)
            emit(Result.success(video))
        }.catch { exception ->
            emit(Result.failure(exception))
        }

}