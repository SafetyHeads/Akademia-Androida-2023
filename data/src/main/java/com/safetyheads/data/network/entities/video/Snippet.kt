package com.safetyheads.data.network.entities.video

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val liveBroadcastContent: String,
    val publishTime: String,
    val publishedAt: String,
    val thumbnails: com.safetyheads.data.network.entities.video.Thumbnails,
    val title: String
)