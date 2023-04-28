package com.safetyheads.akademiaandroida.domain.usecases

import com.safetyheads.akademiaandroida.domain.entities.Channel
import com.safetyheads.akademiaandroida.domain.repositories.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChannelUseCase(private val channelRepository: com.safetyheads.akademiaandroida.domain.repositories.ChannelRepository):
    ParameterlessBaseUseCase<com.safetyheads.akademiaandroida.domain.entities.Channel> {

    override suspend fun invoke(): Flow<Result<com.safetyheads.akademiaandroida.domain.entities.Channel>> {
        return flow {
            try {
                channelRepository.getChannel().collect { channel ->
                    emit(channel)
                }
            } catch (error: Exception) {
                emit(Result.failure(error))
            }
        }
    }
}