package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.data.network.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface VideoUseCase {

    suspend fun execute(previousFilmDate: String): Flow<NetworkResult<com.safetyheads.data.network.entities.video.YouTubeVideoDataClass>>
}