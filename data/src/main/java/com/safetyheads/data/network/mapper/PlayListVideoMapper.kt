package com.safetyheads.data.network.mapper

import com.safetyheads.data.network.entities.playlistitems.PlayListItems
import com.safetyheads.domain.entities.Video
import org.jsoup.Jsoup

class PlayListVideoMapper : BaseMapperRepository<PlayListItems, ArrayList<Video>> {

    override fun transform(type: PlayListItems): ArrayList<Video> {
        val playlistList: ArrayList<Video> = ArrayList()

        type.items.forEach { playlistItems ->
            val videoId: String = playlistItems.id
            val videoTitle: String = Jsoup.parse(playlistItems.snippet.title).text()
            val publishTime: String = Jsoup.parse(playlistItems.snippet.publishedAt).text()
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