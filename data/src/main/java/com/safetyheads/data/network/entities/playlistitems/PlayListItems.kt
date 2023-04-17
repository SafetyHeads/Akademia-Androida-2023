package com.safetyheads.data.network.entities.playlistitems

data class PlayListItems(
    val etag: String = "",
    var items: List<com.safetyheads.data.network.entities.playlistitems.Item> = arrayListOf(),
    val kind: String = "",
    val pageInfo: com.safetyheads.data.network.entities.playlistitems.PageInfo = com.safetyheads.data.network.entities.playlistitems.PageInfo()
)