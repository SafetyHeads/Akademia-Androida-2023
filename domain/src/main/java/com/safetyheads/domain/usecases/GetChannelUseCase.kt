package com.safetyheads.domain.usecases

import com.safetyheads.domain.entities.Channel
import com.safetyheads.domain.repositories.ChannelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChannelUseCase(private val channelRepository: ChannelRepository):
    ParameterlessBaseUseCase<Channel> {

    override suspend fun invoke(): Flow<Result<Channel>> {
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