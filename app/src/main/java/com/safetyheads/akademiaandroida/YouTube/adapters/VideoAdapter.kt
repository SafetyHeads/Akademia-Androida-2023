package com.safetyheads.akademiaandroida.YouTube.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.YouTube.diffUtil.VideoDiffUtil
import com.safetyheads.data.network.entities.video.Item
import com.safetyheads.data.network.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.databinding.ItemVideoBinding
import com.safetyheads.akademiaandroida.network.YouTubeApi


class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = ArrayList<com.safetyheads.data.network.entities.video.YouTubeVideoDataClass>()
    private var clickListener: ClickListener? = null

    class VideoHolder(itemView: ItemVideoBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: com.safetyheads.data.network.entities.video.Item) {
            binding.ivThumbnail.setOnClickListener {
                // navigate to YouTube
            }
            binding.tvVideoTitle.text = HtmlCompat.fromHtml(data.snippet.title, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            binding.tvPublished.text = HtmlCompat.fromHtml(data.snippet.publishedAt, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            Glide.with(binding.root)
                .load(data.snippet.thumbnails.high.url)
                .into(binding.ivThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoHolder).setData(oldItems[position].items[0])
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newList: ArrayList<com.safetyheads.data.network.entities.video.YouTubeVideoDataClass>) {
        val videoDiff = VideoDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        if (newList.size > 0 && newList[newList.size - 1].items[0].snippet.thumbnails.high.url == YouTubeApi.YOUTUBE_DEFAULT_URL)
            clickListener?.onClick()
        else {
            oldItems.clear()
            oldItems.addAll(newList)
        }
        diff.dispatchUpdatesTo(this)
    }

    fun clearAll() {
        oldItems.clear()
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ClickListener) {
        clickListener = listener
    }

    interface ClickListener {
        fun onClick()
    }
}