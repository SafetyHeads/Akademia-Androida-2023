package com.safetyheads.akademiaandroida.YouTube.useCases

import com.safetyheads.akademiaandroida.YouTube.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface DateUseCase {

    fun actualDate(): String
    fun updateDate(actualVideoDate: String): String
}