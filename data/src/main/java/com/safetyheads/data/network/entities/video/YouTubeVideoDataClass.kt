package com.safetyheads.data.network.entities.video

data class YouTubeVideoDataClass(
    val etag: String = "",
    val items: List<com.safetyheads.data.network.entities.video.Item> = mutableListOf(),
    val kind: String = "",
    val nextPageToken: String = "",
    val pageInfo: com.safetyheads.data.network.entities.video.PageInfo = com.safetyheads.data.network.entities.video.PageInfo(),
    val regionCode: String = ""
)