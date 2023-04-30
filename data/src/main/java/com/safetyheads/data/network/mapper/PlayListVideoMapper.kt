package com.safetyheads.data.network.mapper

import com.safetyheads.data.network.entities.playlistitems.PlayListItems
import com.safetyheads.akademiaandroida.domain.entities.Video
import org.jsoup.Jsoup

class PlayListVideoMapper : BaseMapperRepository<PlayListItems, ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video>> {

    override fun transform(type: PlayListItems): ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video> {
        val playlistList: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video> = ArrayList()

        type.items.forEach { playlistItems ->
            val videoId: String = playlistItems.id
            val videoTitle: String = Jsoup.parse(playlistItems.snippet.title).text()
            val publishTime: String = Jsoup.parse(playlistItems.snippet.publishedAt).text()
            val thumbnailsUrl: String = playlistItems.snippet.thumbnails.high.url

            val tempPlaylistItem = com.safetyheads.akademiaandroida.domain.entities.Video(
                videoId,
                videoTitle,
                publishTime,
                thumbnailsUrl
            )

            playlistList.add(tempPlaylistItem)
        }
        return playlistList
    }

    override fun transformToRepository(type: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video>): PlayListItems {
        TODO("Not yet implemented")
    }
}