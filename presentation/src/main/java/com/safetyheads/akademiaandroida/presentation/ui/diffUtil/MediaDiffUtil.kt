package com.safetyheads.akademiaandroida.presentation.ui.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.safetyheads.akademiaandroida.domain.entities.Media

class MediaDiffUtil(
    private val oldList: ArrayList<Media>,
    private val newList: ArrayList<Media>
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
        return oldVideo.mediaUrl == newVideo.mediaUrl
    }
}