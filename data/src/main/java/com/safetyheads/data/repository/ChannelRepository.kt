package com.safetyheads.data.repository

import com.safetyheads.data.network.entities.channel.ChannelDataClass
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    suspend fun getYouTubeChannel(): Flow<Result<ChannelDataClass>>
}