package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.Video
import com.safetyheads.domain.repositories.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPlayListItemsUseCase(private val playlistRepository: PlaylistRepository): BaseUseCase<GetPlayListItemsUseCase.PlayListItemsParams, ArrayList<Video>> {

    class PlayListItemsParams(val playListId: String): BaseUseCase.Params

    override suspend fun invoke(parameter: PlayListItemsParams): Flow<Result<ArrayList<Video>>> {
        return flow {
            try {
                playlistRepository.getPlayListItems(parameter.playListId).collect{ playlistItems ->
                    emit(playlistItems)
                }
            }   catch (error: Exception) {
                emit(Result.failure(error))
            }
        }
    }
}