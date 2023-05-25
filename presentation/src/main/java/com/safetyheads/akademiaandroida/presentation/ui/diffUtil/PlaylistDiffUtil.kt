<<<<<<<< HEAD:presentation/src/main/java/com/safetyheads/akademiaandroida/presentation/ui/diffUtil/PlaylistDiffUtil.kt
package com.safetyheads.akademiaandroida.presentation.ui.diffUtil
========
package com.safetyheads.akademiaandroida.presentation.ui.fragments.youtube
>>>>>>>> master:presentation/src/main/java/com/safetyheads/akademiaandroida/presentation/ui/fragments/youtube/PlaylistDiffUtil.kt

import androidx.recyclerview.widget.DiffUtil

class PlaylistDiffUtil(
    private val oldList: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist>,
    private val newList: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldList[oldItemPosition]
        val newVideo = newList[newItemPosition]
        return oldVideo.playlistId == newVideo.playlistId
    }
}