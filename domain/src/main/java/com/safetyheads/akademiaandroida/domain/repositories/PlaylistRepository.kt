package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.Video
import com.safetyheads.akademiaandroida.domain.entities.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun getPlayLists(): Flow<Result<ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist>>>
    suspend fun getPlayListItems(playListID: String): Flow<Result<ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video>>>
}