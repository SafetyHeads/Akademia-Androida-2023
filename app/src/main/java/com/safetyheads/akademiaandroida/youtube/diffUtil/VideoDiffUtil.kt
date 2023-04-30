package com.safetyheads.akademiaandroida.youtube.diffUtil

import androidx.recyclerview.widget.DiffUtil

class VideoDiffUtil(
    private val oldList: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video>,
    private val newList: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video>
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