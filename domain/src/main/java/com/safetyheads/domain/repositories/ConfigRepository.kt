package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.Config
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {

    suspend fun getConfig() : Flow<Config>
}