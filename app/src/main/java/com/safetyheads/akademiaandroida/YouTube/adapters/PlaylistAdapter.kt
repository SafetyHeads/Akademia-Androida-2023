package com.safetyheads.akademiaandroida.YouTube.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.YouTube.diffUtil.PlaylistDiffUtil
import com.safetyheads.data.network.entities.playlists.Item
import com.safetyheads.data.network.entities.playlists.PlayListsDataClass
import com.safetyheads.akademiaandroida.databinding.ItemPlaylistBinding

class PlaylistAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = com.safetyheads.data.network.entities.playlists.PlayListsDataClass()
    private var clickListener: ClickListener? = null

    class PlaylistHolder(itemView: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: com.safetyheads.data.network.entities.playlists.Item, position: Int, clickListener: ClickListener?) {
            binding.tvPlaylistTitle.text = HtmlCompat.fromHtml(data.snippet.title, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            binding.tvVideoCount.text = HtmlCompat.fromHtml("${data.contentDetails.itemCount} videos", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            Glide.with(binding.root).load(data.snippet.thumbnails.high.url)
                .into(binding.thumbnail)
            binding.thumbnail.setOnClickListener {
                clickListener?.onClick(data, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlaylistHolder).setData(oldItems.items[position], position, clickListener)
    }

    override fun getItemCount(): Int {
        return oldItems.items.size
    }

    fun setData(newItems: com.safetyheads.data.network.entities.playlists.PlayListsDataClass) {
        val playlistDiff = PlaylistDiffUtil(oldItems, newItems)
        val diff = DiffUtil.calculateDiff(playlistDiff)
        newItems.items = newItems.items.filter { it.contentDetails.itemCount != 0 }
        oldItems = newItems
        diff.dispatchUpdatesTo(this)
    }

    fun setClickListener(listener: ClickListener) {
        clickListener = listener
    }

    interface ClickListener {
        fun onClick(item: com.safetyheads.data.network.entities.playlists.Item, position: Int)
    }
}