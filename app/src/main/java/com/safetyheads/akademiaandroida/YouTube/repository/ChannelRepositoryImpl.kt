package com.safetyheads.akademiaandroida.YouTube.repository

import com.safetyheads.akademiaandroida.YouTube.entities.channel.ChannelDataClass
import com.safetyheads.akademiaandroida.network.ApiClient
import com.safetyheads.akademiaandroida.network.NetworkResult
import com.safetyheads.akademiaandroida.network.YouTubeApi
import com.safetyheads.akademiaandroida.network.YouTubeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ChannelRepositoryImpl() : ChannelRepository {

    override suspend fun getYouTubeChannel(): Flow<NetworkResult<ChannelDataClass>> =
        flow {
            val retrofitYouTubeChannel =
                ApiClient().create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java)
                    .getChannel(
                        "",
                        YouTubeApi.YOUTUBE_API_PART_CHANNEL,
                        YouTubeApi.YOUTUBE_CHANNEL_ID
                    )
            emit(NetworkResult.success(retrofitYouTubeChannel))
        }.catch { exception ->
            emit(NetworkResult.Error(Exception(exception)))
        }
}


