package com.safetyheads.akademiaandroida.YouTube.entities.playlistitems

data class PlayListItemsDataClass(
    val etag: String = "",
    var items: List<Item> = arrayListOf(),
    val kind: String = "",
    val pageInfo: PageInfo = PageInfo()
)