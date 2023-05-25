package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.Media
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getInstagramImages(): Flow<Result<List<Media>>>
}