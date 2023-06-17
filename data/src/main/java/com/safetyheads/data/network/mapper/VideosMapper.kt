package com.safetyheads.data.network.mapper

import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.domain.entities.MediaType
import com.safetyheads.data.network.entities.video.YouTubeVideo
import org.jsoup.Jsoup

class VideosMapper : BaseMapperRepository<YouTubeVideo, ArrayList<Media>> {

    override fun transform(type: YouTubeVideo): ArrayList<Media> {
        val videoList: ArrayList<Media> = ArrayList()

        type.items.forEach { videoitem ->
            val videoId: String = videoitem.id.videoId.orEmpty()
            val videoTitle: String = Jsoup.parse(videoitem.snippet.title).text().orEmpty()
            val publishTime: String = Jsoup.parse(videoitem.snippet.publishedAt).text().orEmpty()
            val thumbnailsUrl: String = videoitem.snippet.thumbnails.high.url.orEmpty()

            val videoItem = Media(
                videoId,
                videoTitle,
                publishTime,
                thumbnailsUrl,
                MediaType.VIDEO
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