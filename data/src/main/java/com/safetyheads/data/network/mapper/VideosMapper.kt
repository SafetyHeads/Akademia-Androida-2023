package com.safetyheads.data.network.mapper

import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.data.network.entities.video.YouTubeVideo
import org.jsoup.Jsoup

class VideosMapper : BaseMapperRepository<YouTubeVideo, ArrayList<Media>> {

    override fun transform(type: YouTubeVideo): ArrayList<Media> {
        val videoList: ArrayList<Media> = ArrayList()

        type.items.forEach { videoitem ->
            val videoId: String = videoitem.id.videoId ?: ""
            val videoTitle: String = Jsoup.parse(videoitem.snippet.title).text() ?: ""
            val publishTime: String = Jsoup.parse(videoitem.snippet.publishedAt).text() ?: ""
            val thumbnailsUrl: String = videoitem.snippet.thumbnails.high.url ?: ""

            val videoItem = Media(
                videoId,
                videoTitle,
                publishTime,
                thumbnailsUrl,
                "video"
            )

            if (videoItem.mediaId.isNotEmpty())
                videoList.add(videoItem)
        }

        return videoList
    }

    override fun transformToRepository(type: ArrayList<Media>): YouTubeVideo {
        TODO("Not yet implemented")
    }
}