package com.safetyheads.data.repositories

import com.safetyheads.data.entities.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getVideo(previousFilmDate: String): Flow<Result<Video>>
}