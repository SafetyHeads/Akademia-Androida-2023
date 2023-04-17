package com.safetyheads.data.repositories

import com.safetyheads.data.entities.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    fun getVideo(videoId: String): Flow<Video>

}