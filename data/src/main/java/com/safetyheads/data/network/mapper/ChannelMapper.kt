package com.safetyheads.data.network.mapper

import com.safetyheads.data.network.entities.channel.ChannelInfo
import com.safetyheads.akademiaandroida.domain.entities.Channel
import org.jsoup.Jsoup


class ChannelMapper : BaseMapperRepository<ChannelInfo, Channel> {

    override fun transform(type: ChannelInfo): Channel {
        var channel = Channel()

        val channelName: String = Jsoup.parse(type.items.first().snippet.title).text()
        val channelDescription: String = Jsoup.parse(type.items.first().snippet.description).text()
        val channelBannerUrl: String = type.items.first().brandingSettings.image.bannerExternalUrl
        val channelAvatarUrl: String = type.items.first().snippet.thumbnails.high.url

        if (channelAvatarUrl.isNotEmpty()
            && channelBannerUrl.isNotEmpty()
        ) {
            channel = Channel(
                channelName,
                channelDescription,
                channelBannerUrl,
                channelAvatarUrl
            )
        }

        return channel
    }

    override fun transformToRepository(type: Channel): ChannelInfo {
        TODO("Not yet implemented")
    }
}