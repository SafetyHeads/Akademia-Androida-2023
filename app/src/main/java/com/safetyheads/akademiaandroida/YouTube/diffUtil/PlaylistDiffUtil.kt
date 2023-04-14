package com.safetyheads.akademiaandroida.YouTube.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.safetyheads.data.network.entities.playlists.PlayListsDataClass

class PlaylistDiffUtil(
    private val oldList: com.safetyheads.data.network.entities.playlists.PlayListsDataClass,
    private val newList: com.safetyheads.data.network.entities.playlists.PlayListsDataClass
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.items.size
    }

    override fun getNewListSize(): Int {
        return newList.items.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.items[oldItemPosition] == newList.items[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldList.items[oldItemPosition]
        val newVideo = newList.items[newItemPosition]
        return oldVideo.id == newVideo.id
    }
}