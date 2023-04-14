package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.data.repository.PlaylistRepository
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayListsUseCaseImpl(private val playlistRepository: PlaylistRepository) : PlayListsUseCase {

    override suspend fun execute(): Flow<NetworkResult<com.safetyheads.data.network.entities.playlists.PlayListsDataClass>> {
        return flow {
            emit(NetworkResult.loading())
            try {
                playlistRepository.getYouTubePlayLists().collect { playLists ->
                    emit(playLists)
                }
            } catch (error: Exception) {
                emit(NetworkResult.error(error))
            }
        }
    }
}