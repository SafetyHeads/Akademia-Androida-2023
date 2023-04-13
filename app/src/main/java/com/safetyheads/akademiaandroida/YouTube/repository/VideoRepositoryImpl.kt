package com.safetyheads.akademiaandroida.YouTube.repository

import com.safetyheads.akademiaandroida.BuildConfig
import com.safetyheads.akademiaandroida.YouTube.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.network.ApiClient
import com.safetyheads.akademiaandroida.network.NetworkResult
import com.safetyheads.akademiaandroida.network.YouTubeApi
import com.safetyheads.akademiaandroida.network.YouTubeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VideoRepositoryImpl() : VideoRepository {

    override suspend fun getYouTubeVideo(previousFilmDate: String): Flow<NetworkResult<YouTubeVideoDataClass>> =
        flow {
            val retrofitYouTubeVideo =
                ApiClient().create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java)
                    .getVideo(
                        BuildConfig.YOUTUBE_DATA_API_KEY,
                        YouTubeApi.YOUTUBE_CHANNEL_ID,
                        YouTubeApi.YOUTUBE_API_PART_VIDEO,
                        YouTubeApi.YOUTUBE_API_ORDER,
                        YouTubeApi.YOUTUBE_API_VIDEO_MAX_RESULTS,
                        previousFilmDate
                    )
            emit(NetworkResult.success(retrofitYouTubeVideo))
        }.catch {exception ->
            emit(NetworkResult.Error(Exception(exception)))
        }

}