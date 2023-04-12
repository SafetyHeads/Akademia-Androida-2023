package com.safetyheads.akademiaandroida.YouTube.repository

import com.safetyheads.akademiaandroida.YouTube.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getYouTubeVideo(previousFilmDate: String): Flow<NetworkResult<YouTubeVideoDataClass>>
}