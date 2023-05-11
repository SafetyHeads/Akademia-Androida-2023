package com.safetyheads.data.network.entities.video

data class YouTubeVideo(
    val etag: String = "",
    val items: List<Item> = mutableListOf(),
    val kind: String = "",
    val nextPageToken: String = "",
    val pageInfo: PageInfo = PageInfo(),
    val regionCode: String = ""
)