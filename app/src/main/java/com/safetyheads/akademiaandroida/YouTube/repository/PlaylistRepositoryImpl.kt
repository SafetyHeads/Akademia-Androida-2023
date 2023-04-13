package com.safetyheads.akademiaandroida.YouTube.repository

import com.safetyheads.akademiaandroida.BuildConfig
import com.safetyheads.akademiaandroida.YouTube.entities.playlistitems.PlayListItemsDataClass
import com.safetyheads.akademiaandroida.YouTube.entities.playlists.PlayListsDataClass
import com.safetyheads.akademiaandroida.network.ApiClient
import com.safetyheads.akademiaandroida.network.NetworkResult
import com.safetyheads.akademiaandroida.network.YouTubeApi
import com.safetyheads.akademiaandroida.network.YouTubeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl : PlaylistRepository {

    override suspend fun getYouTubePlayLists(): Flow<NetworkResult<PlayListsDataClass>> =
        flow {
            val retrofitYouTubePlayLists =
                ApiClient().create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java)
                    .getPlayLists(
                        BuildConfig.YOUTUBE_DATA_API_KEY,
                        YouTubeApi.YOUTUBE_API_PART_PLAYLISTS,
                        YouTubeApi.YOUTUBE_CHANNEL_ID
                    )
            emit(NetworkResult.success(retrofitYouTubePlayLists))
        }.catch { exception ->
            emit(NetworkResult.Error(Exception(exception)))
        }

    override suspend fun getYouTubePlayListItems(playListID: String): Flow<NetworkResult<PlayListItemsDataClass>> =
        flow {
            val retrofitYouTubePlayListItems =
                ApiClient().create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java)
                    .getPlayListItems(
                        BuildConfig.YOUTUBE_DATA_API_KEY,
                        YouTubeApi.YOUTUBE_API_PART_PLAYLIST_ITEMS,
                        playListID,
                        YouTubeApi.YOUTUBE_API_PLAYLIST_ITEMS_MAX_RESULT
                    )
            emit(NetworkResult.success(retrofitYouTubePlayListItems))
        }.catch { exception ->
            emit(NetworkResult.Error(Exception(exception)))
        }

}