package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.Config
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {

    fun getConfig(): Flow<Config>
}
