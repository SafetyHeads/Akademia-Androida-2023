package com.safetyheads.akademiaandroida.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.domain.entities.MediaType
import com.safetyheads.akademiaandroida.presentation.databinding.ItemMediaBinding
import com.safetyheads.akademiaandroida.presentation.databinding.ItemVideoBinding
import com.safetyheads.akademiaandroida.presentation.ui.diffUtil.MediaDiffUtil


class MediaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer(this, MediaDiffUtil())
    private var clickListener: ClickListener? = null

    private val viewTypeInstagram = 0
    private val viewTypeVideo = 1

    override fun getItemViewType(position: Int): Int {
        val media = differ.currentList[position]
        return if (media.mediaType == MediaType.INSTAGRAM) {
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

        fun setData(data: Media, clickListener: ClickListener?) {
            Glide.with(binding.root)
                .load(data.mediaUrl)
                .fitCenter()
                .dontAnimate()
                .into(binding.ivThumbnail)
            binding.tvVideoTitle.text = data.mediaTitle
            binding.ivThumbnail.setOnClickListener {
                clickListener?.onClick(data.mediaId)
            }
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
        val media = differ.currentList[position]

        when (holder) {
            is InstagramHolder -> {
                val instagramHolder = holder as InstagramHolder
                instagramHolder.setData(media)
            }
            is VideoHolder -> {
                val videoHolder = holder as VideoHolder
                videoHolder.setData(media, clickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(newList: ArrayList<Media>) {
        differ.submitList(newList)
    }

    fun clearAll() {
        differ.currentList.clear()
        notifyDataSetChanged()
    }

    fun setClickListener(listener: ClickListener) {
        clickListener = listener
    }

    interface ClickListener {
        fun onClick(videoId: String)
    }
}