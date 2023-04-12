package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.akademiaandroida.YouTube.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface VideoUseCase {

    suspend fun execute(previousFilmDate: String): Flow<NetworkResult<YouTubeVideoDataClass>>
}