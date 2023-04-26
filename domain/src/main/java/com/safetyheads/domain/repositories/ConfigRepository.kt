package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.Config
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {

    fun getConfig(): Flow<Config>
}
