package com.safetyheads.akademiaandroida.presentation.ui.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.safetyheads.akademiaandroida.domain.entities.Media

class MediaDiffUtil : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem.mediaUrl == newItem.mediaUrl
    }

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
        return oldItem == newItem
    }
}