package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.data.repository.PlaylistRepository
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayListItemsUseCaseImpl(private val playlistRepository: PlaylistRepository) : PlayListItemsUseCase {

    override suspend fun execute(playListID: String): Flow<NetworkResult<com.safetyheads.data.network.entities.playlistitems.PlayListItemsDataClass>> {
        return flow {
            emit(NetworkResult.loading())
            try {
                playlistRepository.getYouTubePlayListItems(playListID).collect { playlistItems ->
                    emit(playlistItems)
                }
            } catch (error: Exception) {
                emit(NetworkResult.error(error))
            }
        }
    }
}