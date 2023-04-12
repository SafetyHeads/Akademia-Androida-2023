package com.safetyheads.akademiaandroida.YouTube.entities.video

data class YouTubeVideoDataClass(
    val etag: String = "",
    val items: List<Item> = mutableListOf(),
    val kind: String = "",
    val nextPageToken: String = "",
    val pageInfo: PageInfo = PageInfo(),
    val regionCode: String = ""
)