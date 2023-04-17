package com.safetyheads.data.network.mapper

import androidx.core.text.HtmlCompat
import com.safetyheads.data.network.entities.playlistitems.PlayListItems
import com.safetyheads.domain.entities.Video

class PlayListVideoMapper : BaseMapperRepository<PlayListItems, ArrayList<Video>> {

    override fun transform(type: PlayListItems): ArrayList<Video> {
        val playlistList: ArrayList<Video> = ArrayList()

        type.items.forEach { playlistItems ->
            val videoId: String = playlistItems.id
            val videoTitle: String = HtmlCompat.fromHtml(playlistItems.snippet.title, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            val publishTime: String = HtmlCompat.fromHtml(playlistItems.snippet.publishedAt, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            val thumbnailsUrl: String = playlistItems.snippet.thumbnails.high.url

            val tempPlaylistItem = Video(
                videoId,
                videoTitle,
                publishTime,
                thumbnailsUrl
            )

            playlistList.add(tempPlaylistItem)
        }
        return playlistList
    }

    override fun transformToRepository(type: ArrayList<Video>): PlayListItems {
        TODO("Not yet implemented")
    }
}