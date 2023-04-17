package com.safetyheads.data.network.mapper

import androidx.core.text.HtmlCompat
import com.safetyheads.domain.entities.Channel


class ChannelMapper : BaseMapperRepository<com.safetyheads.data.network.entities.channel.ChannelInfo, Channel> {

    override fun transform(type: com.safetyheads.data.network.entities.channel.ChannelInfo): Channel {
        var channel = Channel()

        val channelName: String = HtmlCompat.fromHtml(type.items.first().snippet.title, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        val channelDescription: String = HtmlCompat.fromHtml(type.items.first().snippet.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
        val channelBannerUrl: String = type.items.first().brandingSettings.image.bannerExternalUrl
        val channelAvatarUrl: String = type.items.first().snippet.thumbnails.high.url

        if (channelAvatarUrl.isNotEmpty()
            && channelBannerUrl.isNotEmpty()
        ) {
            channel = Channel(
                channelName,
                channelDescription,
                channelBannerUrl,
                channelAvatarUrl)
        }

        return channel
    }

    override fun transformToRepository(type: Channel): com.safetyheads.data.network.entities.channel.ChannelInfo {
        TODO("Not yet implemented")
    }
}