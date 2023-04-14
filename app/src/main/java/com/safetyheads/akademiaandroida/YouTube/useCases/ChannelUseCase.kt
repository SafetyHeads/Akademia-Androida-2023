package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.data.network.entities.channel.ChannelDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ChannelUseCase {

    suspend fun execute(): Flow<NetworkResult<com.safetyheads.data.network.entities.channel.ChannelDataClass>>
}