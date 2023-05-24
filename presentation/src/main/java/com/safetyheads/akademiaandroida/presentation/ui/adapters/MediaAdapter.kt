package com.safetyheads.akademiaandroida.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.presentation.databinding.ItemMediaBinding
import com.safetyheads.akademiaandroida.presentation.databinding.ItemVideoBinding
import com.safetyheads.akademiaandroida.presentation.ui.diffUtil.MediaDiffUtil


class MediaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = ArrayList<Media>()

    private val viewTypeInstagram = 0
    private val viewTypeVideo = 1

    override fun getItemViewType(position: Int): Int {
        val media = oldItems[position]
        return if (media.mediaType == "instagram") {
            viewTypeInstagram
        } else {
            viewTypeVideo
        }
    }

    class InstagramHolder(itemView: ItemMediaBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: Media) {
            Glide.with(binding.root)
                .load(data.mediaUrl)
                .into(binding.imageView)
        }
    }

    class VideoHolder(itemView: ItemVideoBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: Media) {
            Glide.with(binding.root)
                .load(data.mediaUrl)
                .dontAnimate()
                .into(binding.ivThumbnail)
            binding.tvVideoTitle.text = data.mediaTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            viewTypeInstagram -> {
                val view = ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                InstagramHolder(view)
            }
            viewTypeVideo -> {
                val view = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VideoHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val media = oldItems[position]

        when (holder) {
            is InstagramHolder -> {
                val instagramHolder = holder as InstagramHolder
                instagramHolder.setData(media)
            }
            is VideoHolder -> {
                val videoHolder = holder as VideoHolder
                videoHolder.setData(media)
            }
        }
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: ArrayList<Media>) {
        val videoDiff = MediaDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems.clear()
        oldItems.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    fun clearAll() {
        oldItems.clear()
        notifyDataSetChanged()
    }
}