package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.data.network.entities.playlistitems.PlayListItemsDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PlayListItemsUseCase {

    suspend fun execute(playListID: String): Flow<NetworkResult<com.safetyheads.data.network.entities.playlistitems.PlayListItemsDataClass>>
}