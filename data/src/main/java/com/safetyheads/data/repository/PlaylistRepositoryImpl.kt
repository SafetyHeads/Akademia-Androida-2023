package com.safetyheads.data.repository

import com.safetyheads.data.network.entities.playlistitems.PlayListItemsDataClass
import com.safetyheads.data.network.entities.playlists.PlayListsDataClass
import com.safetyheads.data.network.retrofit.ApiClient
import com.safetyheads.data.`object`.YouTubeApi
import com.safetyheads.data.service.YouTubeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(private val youTubeService: YouTubeService) : PlaylistRepository {

    override suspend fun getYouTubePlayLists(): Flow<Result<PlayListsDataClass>> =
        flow {
            val retrofitYouTubePlayLists =
                youTubeService
                    .getPlayLists(
                        BuildConfig.YOUTUBE_DATA_API_KEY,
                        YouTubeApi.YOUTUBE_API_PART_PLAYLISTS,
                        YouTubeApi.YOUTUBE_CHANNEL_ID
                    )
            emit(Result.success(retrofitYouTubePlayLists))
        }.catch { exception ->
            emit(Result.failure(Exception(exception)))
        }

    override suspend fun getYouTubePlayListItems(playListID: String): Flow<Result<PlayListItemsDataClass>> =
        flow {
            val retrofitYouTubePlayListItems =
                ApiClient().create(YouTubeApi.YOUTUBE_API_BASE_URL, YouTubeService::class.java)
                    .getPlayListItems(
                        BuildConfig.YOUTUBE_DATA_API_KEY,
                        YouTubeApi.YOUTUBE_API_PART_PLAYLIST_ITEMS,
                        playListID,
                        YouTubeApi.YOUTUBE_API_PLAYLIST_ITEMS_MAX_RESULT
                    )
            emit(Result.success(retrofitYouTubePlayListItems))
        }.catch { exception ->
            emit(Result.failure(Exception(exception)))
        }

}