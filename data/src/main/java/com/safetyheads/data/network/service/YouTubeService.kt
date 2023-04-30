package com.safetyheads.data.network.service

import com.safetyheads.data.network.entities.channel.ChannelInfo
import com.safetyheads.data.network.entities.playlistitems.PlayListItems
import com.safetyheads.data.network.entities.playlists.PlayLists
import com.safetyheads.data.network.entities.video.YouTubeVideo
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
    ) : YouTubeVideo

    @GET("channels")
    suspend fun getChannel(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("id") id: String
    ) : ChannelInfo

    @GET("playlists")
    suspend fun getPlayLists(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("channelId") id: String
    ) : PlayLists

    @GET("playlistItems")
    suspend fun getPlayListItems(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("playlistId") id: String,
        @Query("maxResults") maxResult: Int
    ) : PlayListItems
}