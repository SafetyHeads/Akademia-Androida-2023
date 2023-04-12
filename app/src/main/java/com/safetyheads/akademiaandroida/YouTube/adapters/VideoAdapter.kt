package com.safetyheads.akademiaandroida.YouTube.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.YouTube.diffUtil.VideoDiffUtil
import com.safetyheads.akademiaandroida.YouTube.entities.video.Item
import com.safetyheads.akademiaandroida.YouTube.entities.video.YouTubeVideoDataClass
import com.safetyheads.akademiaandroida.databinding.ItemVideoBinding


class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = ArrayList<YouTubeVideoDataClass>()

    class VideoHolder(itemView: ItemVideoBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView

        fun setData(data: Item){
            binding.ivThumbnail.setOnClickListener {
                // navigate to YouTube
            }
            binding.tvVideoTitle.text = data.snippet.title
            binding.tvPublished.text = data.snippet.publishedAt
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

    fun setData(newList: ArrayList<YouTubeVideoDataClass>){
        val videoDiff = VideoDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems.clear()
        oldItems.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    fun clearAll(){
        oldItems.clear()
        notifyDataSetChanged()
    }
}