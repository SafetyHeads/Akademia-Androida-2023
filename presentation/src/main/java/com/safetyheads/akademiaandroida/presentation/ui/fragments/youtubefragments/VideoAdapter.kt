package com.safetyheads.akademiaandroida.presentation.ui.fragments.youtubefragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.presentation.databinding.ItemVideoBinding
import com.safetyheads.akademiaandroida.presentation.ui.fragments.youtubefragments.diffUtil.VideoDiffUtil


class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video>()

    class VideoHolder(itemView: ItemVideoBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: com.safetyheads.akademiaandroida.domain.entities.Video) {
            binding.ivThumbnail.setOnClickListener {
                // navigate to YouTube
            }
            binding.tvVideoTitle.text = data.videoTitle
            binding.tvPublished.text = data.publishTime
            Glide.with(binding.root)
                .load(data.thumbnailsUrl)
                .into(binding.ivThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoHolder).setData(oldItems[position])
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Video>) {
        val videoDiff = VideoDiffUtil(oldItems, newList)
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