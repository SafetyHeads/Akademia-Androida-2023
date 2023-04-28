package com.safetyheads.data.network.mapper

import com.safetyheads.data.network.entities.playlists.PlayLists
import com.safetyheads.akademiaandroida.domain.entities.Playlist
import org.jsoup.Jsoup

class PlaylistMapper : BaseMapperRepository<PlayLists, ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist>> {

    override fun transform(type: PlayLists): ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist> {
        val playlistList: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist> = ArrayList()

        type.items.forEach { playlistItem ->
            val playlistId: String = playlistItem.id
            val playlistTitle: String = Jsoup.parse(playlistItem.snippet.title).text()
            val playlistVideoCount: Int = playlistItem.contentDetails.itemCount
            val playlistUrl: String = playlistItem.snippet.thumbnails.high.url

            val tempPlaylistItem = com.safetyheads.akademiaandroida.domain.entities.Playlist(
                playlistId,
                playlistTitle,
                playlistVideoCount,
                playlistUrl
            )

            if(tempPlaylistItem.playlistVideoCount != 0)
                playlistList.add(tempPlaylistItem)
        }

        return playlistList
    }

    override fun transformToRepository(type: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist>): PlayLists {
        TODO("Not yet implemented")
    }
}