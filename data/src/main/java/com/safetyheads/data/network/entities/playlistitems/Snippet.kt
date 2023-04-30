package com.safetyheads.data.network.entities.playlistitems

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val playlistId: String,
    val position: Int,
    val publishedAt: String,
    val resourceId: com.safetyheads.data.network.entities.playlistitems.ResourceId,
    val thumbnails: com.safetyheads.data.network.entities.playlistitems.Thumbnails,
    val title: String,
    val videoOwnerChannelId: String,
    val videoOwnerChannelTitle: String
)