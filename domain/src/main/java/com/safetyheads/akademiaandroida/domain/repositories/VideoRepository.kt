package com.safetyheads.akademiaandroida.domain.repositories

import com.safetyheads.akademiaandroida.domain.entities.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getVideo(previousFilmDate: String): Flow<Result<com.safetyheads.akademiaandroida.domain.entities.Video>>
}