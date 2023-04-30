package com.safetyheads.data.network.mapper

import com.safetyheads.data.network.entities.video.YouTubeVideo
import com.safetyheads.akademiaandroida.domain.entities.Video
import org.jsoup.Jsoup

class VideoMapper : BaseMapperRepository<YouTubeVideo, com.safetyheads.akademiaandroida.domain.entities.Video> {

    override fun transform(type: YouTubeVideo): com.safetyheads.akademiaandroida.domain.entities.Video {
        val videoId: String = type.items.first().id.videoId
        val videoTitle: String = Jsoup.parse(type.items.first().snippet.title).text()
        val publishTime: String = Jsoup.parse(type.items.first().snippet.publishedAt).text()
        val thumbnailsUrl: String = type.items.first().snippet.thumbnails.high.url

        return com.safetyheads.akademiaandroida.domain.entities.Video(
            videoId,
            videoTitle,
            publishTime,
            thumbnailsUrl
        )
    }

    override fun transformToRepository(type: com.safetyheads.akademiaandroida.domain.entities.Video): YouTubeVideo {
        TODO("Not yet implemented")
    }
}