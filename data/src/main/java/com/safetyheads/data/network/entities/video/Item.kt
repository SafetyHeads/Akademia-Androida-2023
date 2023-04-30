package com.safetyheads.data.network.entities.video

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)