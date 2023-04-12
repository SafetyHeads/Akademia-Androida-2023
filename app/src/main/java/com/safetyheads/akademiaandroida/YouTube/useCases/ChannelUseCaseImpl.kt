package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.akademiaandroida.YouTube.entities.channel.ChannelDataClass
import com.safetyheads.akademiaandroida.YouTube.repository.ChannelRepository
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChannelUseCaseImpl(private val channelRepository: ChannelRepository): ChannelUseCase {

    override suspend fun execute(): Flow<NetworkResult<ChannelDataClass>> {
        return flow {
            emit(NetworkResult.loading())
            try {
                channelRepository.getYouTubeChannel().collect { channel ->
                    emit(channel)
                }
            } catch (error: Exception) {
                emit(NetworkResult.error(error))
            }
        }
    }
}