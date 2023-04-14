package com.safetyheads.data.repository

import com.safetyheads.data.network.entities.video.YouTubeVideoDataClass
import com.safetyheads.data.`object`.YouTubeApi
import com.safetyheads.data.service.YouTubeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import com.safetyheads.akademiaandroida.BuildConfig

class VideoRepositoryImpl(private val youTubeService: YouTubeService) : VideoRepository {

    override suspend fun getYouTubeVideo(previousFilmDate: String): Flow<Result<YouTubeVideoDataClass>> =
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
            emit(Result.success(retrofitYouTubeVideo))
        }.catch {exception ->
            emit(Result.failure(Exception(exception)))
        }

}