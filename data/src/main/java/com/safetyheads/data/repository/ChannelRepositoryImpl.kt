package com.safetyheads.data.repository

import com.safetyheads.data.network.entities.channel.ChannelDataClass
import com.safetyheads.data.`object`.YouTubeApi
import com.safetyheads.data.service.YouTubeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ChannelRepositoryImpl(private val youTubeService: YouTubeService) : ChannelRepository {

    override suspend fun getYouTubeChannel(): Flow<Result<ChannelDataClass>> =
        flow {
            val retrofitYouTubeChannel =
                youTubeService
                    .getChannel(
                        BuildConfig.YOUTUBE_DATA_API_KEY,
                        YouTubeApi.YOUTUBE_API_PART_CHANNEL,
                        YouTubeApi.YOUTUBE_CHANNEL_ID
                    )
            emit(Result.success(retrofitYouTubeChannel))
        }.catch { exception ->
            emit(Result.failure(Exception(exception)))
        }
}


