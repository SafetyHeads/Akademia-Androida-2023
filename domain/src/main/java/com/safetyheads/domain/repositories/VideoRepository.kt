package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getVideo(previousFilmDate: String): Flow<Result<Video>>
}