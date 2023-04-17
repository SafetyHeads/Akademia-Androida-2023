package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.Playlist
import com.safetyheads.domain.entities.Video
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun getPlayLists(): Flow<Result<ArrayList<Playlist>>>
    suspend fun getPlayListItems(playListID: String): Flow<Result<ArrayList<Video>>>
}