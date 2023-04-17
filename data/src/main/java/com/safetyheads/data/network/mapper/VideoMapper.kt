package com.safetyheads.data.network.mapper

import androidx.core.text.HtmlCompat
import com.safetyheads.data.network.entities.video.YouTubeVideo
import com.safetyheads.domain.entities.Video

class VideoMapper : BaseMapperRepository<YouTubeVideo, Video> {

    override fun transform(type: YouTubeVideo): Video {
        val videoId: String = type.items.first().id.videoId
        val videoTitle: String =
            HtmlCompat.fromHtml(type.items.first().snippet.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
                .toString()
        val publishTime: String = HtmlCompat.fromHtml(
            type.items.first().snippet.publishedAt,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
        val thumbnailsUrl: String = type.items.first().snippet.thumbnails.high.url

        return Video(
            videoId,
            videoTitle,
            publishTime,
            thumbnailsUrl
        )
    }

    override fun transformToRepository(type: Video): YouTubeVideo {
        TODO("Not yet implemented")
    }
}