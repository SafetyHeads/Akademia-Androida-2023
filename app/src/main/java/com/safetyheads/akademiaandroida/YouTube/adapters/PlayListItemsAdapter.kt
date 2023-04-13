package com.safetyheads.akademiaandroida.YouTube.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.YouTube.diffUtil.PlayListItemsDiffUtil
import com.safetyheads.akademiaandroida.YouTube.entities.playlistitems.PlayListItemsDataClass
import com.safetyheads.akademiaandroida.databinding.ItemVideoBinding


class PlayListItemsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = PlayListItemsDataClass()

    class VideoHolder(itemView: ItemVideoBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: com.safetyheads.akademiaandroida.YouTube.entities.playlistitems.Item) {
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
        (holder as VideoHolder).setData(oldItems.items[position])
    }

    override fun getItemCount(): Int {
        return oldItems.items.size
    }

    fun setData(newItems: PlayListItemsDataClass) {
        val videoDiff = PlayListItemsDiffUtil(oldItems, newItems)
        val diff = DiffUtil.calculateDiff(videoDiff)
        newItems.items = newItems.items.filter { it.snippet.thumbnails.high != null }
        oldItems = newItems
        diff.dispatchUpdatesTo(this)
    }

    fun clearAll() {
        oldItems.items = mutableListOf()
        notifyDataSetChanged()
    }
}