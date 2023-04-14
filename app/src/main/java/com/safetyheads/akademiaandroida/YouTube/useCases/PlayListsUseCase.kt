package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.data.network.entities.channel.ChannelDataClass
import com.safetyheads.data.network.entities.playlists.PlayListsDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PlayListsUseCase {

    suspend fun execute(): Flow<NetworkResult<com.safetyheads.data.network.entities.playlists.PlayListsDataClass>>
}