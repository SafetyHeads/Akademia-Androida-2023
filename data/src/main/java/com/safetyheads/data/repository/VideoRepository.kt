package com.safetyheads.data.repository

import com.safetyheads.data.network.entities.video.YouTubeVideoDataClass
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getYouTubeVideo(previousFilmDate: String): Flow<Result<YouTubeVideoDataClass>>
}