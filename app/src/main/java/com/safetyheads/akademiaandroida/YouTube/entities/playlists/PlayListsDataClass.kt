package com.safetyheads.akademiaandroida.YouTube.entities.playlists

data class PlayListsDataClass(
    val etag: String = "",
    var items: List<Item> = mutableListOf(),
    val kind: String = "",
    val pageInfo: PageInfo = PageInfo()
)