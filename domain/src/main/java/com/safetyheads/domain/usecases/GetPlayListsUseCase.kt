package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.Playlist
import com.safetyheads.domain.repositories.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPlayListsUseCase(private val playlistRepository: PlaylistRepository): BaseUseCase<GetPlayListsUseCase.PlayListsParam, ArrayList<Playlist>> {

    class PlayListsParam : BaseUseCase.Params

    override suspend fun invoke(parameter: PlayListsParam): Flow<Result<ArrayList<Playlist>>> {
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