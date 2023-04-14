package com.safetyheads.data.repository

import com.safetyheads.data.network.entities.playlistitems.PlayListItemsDataClass
import com.safetyheads.data.network.entities.playlists.PlayListsDataClass
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun getYouTubePlayLists(): Flow<Result<PlayListsDataClass>>
    suspend fun getYouTubePlayListItems(playListID: String): Flow<Result<PlayListItemsDataClass>>
}