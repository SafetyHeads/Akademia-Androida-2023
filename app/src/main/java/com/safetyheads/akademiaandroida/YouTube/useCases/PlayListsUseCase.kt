package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.akademiaandroida.YouTube.entities.channel.ChannelDataClass
import com.safetyheads.akademiaandroida.YouTube.entities.playlists.PlayListsDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PlayListsUseCase {

    suspend fun execute(): Flow<NetworkResult<PlayListsDataClass>>
}