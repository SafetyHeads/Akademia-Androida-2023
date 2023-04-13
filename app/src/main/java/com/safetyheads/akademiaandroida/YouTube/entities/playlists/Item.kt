package com.safetyheads.akademiaandroida.YouTube.entities.playlists

data class Item(
    val contentDetails: ContentDetails,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: Snippet
)