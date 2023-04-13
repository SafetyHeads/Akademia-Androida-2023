package com.safetyheads.akademiaandroida.YouTube.repository

import com.safetyheads.akademiaandroida.YouTube.entities.playlistitems.PlayListItemsDataClass
import com.safetyheads.akademiaandroida.YouTube.entities.playlists.PlayListsDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun getYouTubePlayLists(): Flow<NetworkResult<PlayListsDataClass>>
    suspend fun getYouTubePlayListItems(playListID: String): Flow<NetworkResult<PlayListItemsDataClass>>
}