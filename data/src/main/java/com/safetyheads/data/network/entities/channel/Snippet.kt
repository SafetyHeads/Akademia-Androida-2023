package com.safetyheads.data.network.entities.channel

data class Snippet(
    val customUrl: String,
    val description: String,
    val localized: com.safetyheads.data.network.entities.channel.Localized,
    val publishedAt: String,
    val thumbnails: com.safetyheads.data.network.entities.channel.Thumbnails,
    val title: String
)