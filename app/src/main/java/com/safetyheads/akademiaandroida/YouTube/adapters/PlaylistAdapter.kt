package com.safetyheads.akademiaandroida.YouTube.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.YouTube.diffUtil.PlaylistDiffUtil
import com.safetyheads.akademiaandroida.databinding.ItemPlaylistBinding
import com.safetyheads.akademiaandroida.domain.entities.Playlist

class PlaylistAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist>()
    private var clickListener: ClickListener? = null

    class PlaylistHolder(itemView: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: com.safetyheads.akademiaandroida.domain.entities.Playlist, position: Int, clickListener: ClickListener?) {
            binding.tvPlaylistTitle.text = data.playlistTitle
            binding.tvVideoCount.text = "${data.playlistVideoCount} videos"
            Glide.with(binding.root).load(data.playlistUrl)
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
        (holder as PlaylistHolder).setData(oldItems[position], position, clickListener)
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    fun setData(newItems: ArrayList<com.safetyheads.akademiaandroida.domain.entities.Playlist>) {
        val playlistDiff = PlaylistDiffUtil(oldItems, newItems)
        val diff = DiffUtil.calculateDiff(playlistDiff)
        oldItems = newItems
        diff.dispatchUpdatesTo(this)
    }

    fun setClickListener(listener: ClickListener) {
        clickListener = listener
    }

    interface ClickListener {
        fun onClick(item: com.safetyheads.akademiaandroida.domain.entities.Playlist, position: Int)
    }
}