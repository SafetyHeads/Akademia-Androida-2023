package com.safetyheads.akademiaandroida.YouTube.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.safetyheads.data.network.entities.video.YouTubeVideoDataClass

class VideoDiffUtil(
    private val oldList: List<com.safetyheads.data.network.entities.video.YouTubeVideoDataClass>,
    private val newList: List<com.safetyheads.data.network.entities.video.YouTubeVideoDataClass>
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
        return oldVideo.items[0].id.videoId == newVideo.items[0].id.videoId
    }
}