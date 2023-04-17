package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    suspend fun getChannel(): Flow<Result<Channel>>
}