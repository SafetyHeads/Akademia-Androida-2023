package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.akademiaandroida.YouTube.entities.playlistitems.PlayListItemsDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PlayListItemsUseCase {

    suspend fun execute(playListID: String): Flow<NetworkResult<PlayListItemsDataClass>>
}