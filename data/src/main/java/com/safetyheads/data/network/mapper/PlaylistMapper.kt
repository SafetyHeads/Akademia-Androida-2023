package com.safetyheads.data.network.mapper

import androidx.core.text.HtmlCompat
import com.safetyheads.data.network.entities.playlists.PlayLists
import com.safetyheads.domain.entities.Playlist

class PlaylistMapper : BaseMapperRepository<PlayLists, ArrayList<Playlist>> {

    override fun transform(type: PlayLists): ArrayList<Playlist> {
        val playlistList: ArrayList<Playlist> = ArrayList()

        type.items.forEach { playlistItem ->
            val playlistId: String = playlistItem.id
            val playlistTitle: String = HtmlCompat.fromHtml(playlistItem.snippet.title, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
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