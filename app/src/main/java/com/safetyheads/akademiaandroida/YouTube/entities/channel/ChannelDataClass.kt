package com.safetyheads.akademiaandroida.YouTube.entities.channel

data class ChannelDataClass(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val pageInfo: PageInfo
)