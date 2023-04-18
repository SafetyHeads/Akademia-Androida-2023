package com.safetyheads.data.network.mapper

import com.safetyheads.data.network.entities.playlists.PlayLists
import com.safetyheads.domain.entities.Playlist
import org.jsoup.Jsoup

class PlaylistMapper : BaseMapperRepository<PlayLists, ArrayList<Playlist>> {

    override fun transform(type: PlayLists): ArrayList<Playlist> {
        val playlistList: ArrayList<Playlist> = ArrayList()

        type.items.forEach { playlistItem ->
            val playlistId: String = playlistItem.id
            val playlistTitle: String = Jsoup.parse(playlistItem.snippet.title).text()
            val playlistVideoCount: Int = playlistItem.contentDetails.itemCount
            val playlistUrl: String = playlistItem.snippet.thumbnails.high.url

            val tempPlaylistItem = Playlist(
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

    override fun transformToRepository(type: ArrayList<Playlist>): PlayLists {
        TODO("Not yet implemented")
    }
}