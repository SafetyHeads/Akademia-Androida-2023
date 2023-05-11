package com.safetyheads.data.network.repository

import com.safetyheads.data.network.mapper.ChannelMapper
import com.safetyheads.data.network.service.YouTubeService
import com.safetyheads.akademiaandroida.domain.entities.Channel
import com.safetyheads.akademiaandroida.domain.repositories.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ChannelRepositoryImpl(
    private val youTubeService: YouTubeService,
    private val channelMapper: ChannelMapper,
    private val apiKey: String
) : ChannelRepository {

    override suspend fun getChannel(): Flow<Result<Channel>> =
        flow {
            val retrofitYouTubeChannel =
                youTubeService
                    .getChannel(
                        apiKey,
                        YouTubeApiConsts.YOUTUBE_API_PART_CHANNEL,
                        YouTubeApiConsts.YOUTUBE_CHANNEL_ID
                    )
            val channel = channelMapper.transform(retrofitYouTubeChannel)
            emit(Result.success(channel))
        }.catch { exception ->
            emit(Result.failure(Exception(exception)))
        }
}


