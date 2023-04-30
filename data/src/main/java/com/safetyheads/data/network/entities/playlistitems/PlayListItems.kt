package com.safetyheads.data.network.entities.playlistitems

data class PlayListItems(
    val etag: String = "",
    var items: List<Item> = arrayListOf(),
    val kind: String = "",
    val pageInfo: PageInfo = PageInfo()
)