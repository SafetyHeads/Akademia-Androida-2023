package com.safetyheads.data.network.entities.channel

data class ChannelDataClass(
    val etag: String,
    val items: List<com.safetyheads.data.network.entities.channel.Item>,
    val kind: String,
    val pageInfo: com.safetyheads.data.network.entities.channel.PageInfo
)