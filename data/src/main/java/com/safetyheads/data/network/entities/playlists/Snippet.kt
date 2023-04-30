package com.safetyheads.data.network.entities.playlists

data class Snippet(
    val channelId: String,
    val channelTitle: String,
    val description: String,
    val localized: com.safetyheads.data.network.entities.playlists.Localized,
    val publishedAt: String,
    val thumbnails: com.safetyheads.data.network.entities.playlists.Thumbnails,
    val title: String
)