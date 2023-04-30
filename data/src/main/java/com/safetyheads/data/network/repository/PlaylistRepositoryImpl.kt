package com.safetyheads.data.network.repository

import com.safetyheads.data.network.mapper.PlayListVideoMapper
import com.safetyheads.data.network.mapper.PlaylistMapper
import com.safetyheads.data.network.service.YouTubeService
import com.safetyheads.akademiaandroida.domain.entities.Playlist
import com.safetyheads.akademiaandroida.domain.entities.Video
import com.safetyheads.akademiaandroida.domain.repositories.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val youTubeService: YouTubeService,
    private val playListVideoMapper: PlayListVideoMapper,
    private val playlistMapper: PlaylistMapper,
    private val apiKey: String
) : PlaylistRepository {

    override suspend fun getPlayLists(): Flow<Result<ArrayList<Playlist>>> =
        flow {
            val retrofitYouTubePlayLists =
                youTubeService
                    .getPlayLists(
                        apiKey,
                        YouTubeApiConsts.YOUTUBE_API_PART_PLAYLISTS,
                        YouTubeApiConsts.YOUTUBE_CHANNEL_ID
                    )
            val playlistList = playlistMapper.transform(retrofitYouTubePlayLists)
            emit(Result.success(playlistList))
        }.catch { exception ->
            emit(Result.failure(exception))
        }

    override suspend fun getPlayListItems(playListID: String): Flow<Result<ArrayList<Video>>> =
        flow {
            val retrofitYouTubePlayListItems =
                youTubeService
                    .getPlayListItems(
                        apiKey,
                        YouTubeApiConsts.YOUTUBE_API_PART_PLAYLIST_ITEMS,
                        playListID,
                        YouTubeApiConsts.YOUTUBE_API_PLAYLIST_ITEMS_MAX_RESULT
                    )
            val playlistItems = playListVideoMapper.transform(retrofitYouTubePlayListItems)
            emit(Result.success(playlistItems))
        }.catch { exception ->
            emit(Result.failure(exception))
        }

}