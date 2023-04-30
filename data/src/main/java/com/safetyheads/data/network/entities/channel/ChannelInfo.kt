package com.safetyheads.data.network.entities.channel

data class ChannelInfo(
    val etag: String,
    val items: List<com.safetyheads.data.network.entities.channel.Item>,
    val kind: String,
    val pageInfo: com.safetyheads.data.network.entities.channel.PageInfo
)