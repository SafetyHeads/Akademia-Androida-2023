package com.safetyheads.akademiaandroida.YouTube.repository

import com.safetyheads.akademiaandroida.YouTube.entities.channel.ChannelDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    suspend fun getYouTubeChannel(): Flow<NetworkResult<ChannelDataClass>>
}