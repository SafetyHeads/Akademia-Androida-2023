package com.safetyheads.akademiaandroida.network

import com.safetyheads.akademiaandroida.YouTube.entities.channel.ChannelDataClass
import com.safetyheads.akademiaandroida.YouTube.entities.video.YouTubeVideoDataClass
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeService {
    @GET("search")
    suspend fun getVideo(
        @Query("key") apiKey: String,
        @Query("channelId") channelId: String,
        @Query("part") part: String,
        @Query("order") order: String,
        @Query("maxResults") maxResults: Int,
        @Query("publishedBefore") date: String
    ) : YouTubeVideoDataClass

    @GET("channels")
    suspend fun getChannel(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("id") id: String
    ) : ChannelDataClass
}