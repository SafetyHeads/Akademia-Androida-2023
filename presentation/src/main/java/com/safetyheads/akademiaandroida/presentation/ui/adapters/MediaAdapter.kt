package com.safetyheads.akademiaandroida.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.presentation.databinding.ItemMediaBinding
import com.safetyheads.akademiaandroida.presentation.ui.diffUtil.MediaDiffUtil


class MediaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldItems = ArrayList<Media>()

    class VideoHolder(itemView: ItemMediaBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun setData(data: Media) {
            Glide.with(binding.root)
                .load(data.mediaUrl)
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoHolder).setData(oldItems[position])
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