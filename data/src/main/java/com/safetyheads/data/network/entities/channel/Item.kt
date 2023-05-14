package com.safetyheads.data.network.entities.channel

data class Item(
    val brandingSettings: com.safetyheads.data.network.entities.channel.BrandingSettings,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: com.safetyheads.data.network.entities.channel.Snippet
)