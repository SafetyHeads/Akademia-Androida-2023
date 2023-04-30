package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.Playlist
import com.safetyheads.akademiaandroida.domain.repositories.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPlayListsUseCase(private val playlistRepository: PlaylistRepository) :
    ParameterlessBaseUseCase<ArrayList<Playlist>> {

    override suspend fun invoke(): Flow<Result<ArrayList<Playlist>>> {
        return flow {
            try {
                playlistRepository.getPlayLists().collect { playlists ->
                    emit(playlists)
                }
            } catch (error: Exception) {
                emit(Result.failure(error))
            }
        }
    }
}