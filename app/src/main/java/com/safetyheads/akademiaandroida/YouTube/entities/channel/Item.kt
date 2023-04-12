package com.safetyheads.akademiaandroida.YouTube.entities.channel

data class Item(
    val brandingSettings: BrandingSettings,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet
)