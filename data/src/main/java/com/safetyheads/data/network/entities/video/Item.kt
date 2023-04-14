package com.safetyheads.data.network.entities.video

data class Item(
    val etag: String,
    val id: com.safetyheads.data.network.entities.video.Id,
    val kind: String,
    val snippet: com.safetyheads.data.network.entities.video.Snippet
)