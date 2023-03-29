package com.safetyheads.domain.repositories

import com.safetyheads.domain.entities.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    fun getVideo(videoId: String): Flow<Video>

}