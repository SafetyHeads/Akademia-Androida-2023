package com.safetyheads.data.network.entities.playlists

data class Item(
    val contentDetails: com.safetyheads.data.network.entities.playlists.ContentDetails,
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: com.safetyheads.data.network.entities.playlists.Snippet
)