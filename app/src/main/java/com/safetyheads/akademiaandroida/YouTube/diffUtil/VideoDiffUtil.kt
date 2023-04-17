package com.safetyheads.akademiaandroida.YouTube.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.safetyheads.domain.entities.Video

class VideoDiffUtil(
    private val oldList: ArrayList<Video>,
    private val newList: ArrayList<Video>
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
        return oldVideo.videoId == newVideo.videoId
    }
}