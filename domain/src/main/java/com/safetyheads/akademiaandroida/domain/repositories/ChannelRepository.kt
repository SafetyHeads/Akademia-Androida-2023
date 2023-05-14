package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    suspend fun getChannel(): Flow<Result<Channel>>
}