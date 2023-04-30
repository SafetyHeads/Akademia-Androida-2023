package com.safetyheads.data.network.entities.playlists

data class PlayLists(
    val etag: String = "",
    var items: List<com.safetyheads.data.network.entities.playlists.Item> = mutableListOf(),
    val kind: String = "",
    val pageInfo: com.safetyheads.data.network.entities.playlists.PageInfo = com.safetyheads.data.network.entities.playlists.PageInfo()
)