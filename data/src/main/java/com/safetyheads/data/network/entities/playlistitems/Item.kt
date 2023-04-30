package com.safetyheads.data.network.entities.playlistitems

data class Item(
    val etag: String,
    val id: String,
    val kind: String,
    val snippet: com.safetyheads.data.network.entities.playlistitems.Snippet
)