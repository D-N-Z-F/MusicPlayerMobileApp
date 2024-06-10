package com.example.musicplayermobileapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.core.utils.Constants
import com.example.musicplayermobileapplication.core.utils.SongDiffUtil
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.LayoutSongHorizontalItemBinding
import com.example.musicplayermobileapplication.databinding.LayoutSongVerticalItemBinding
import java.io.File

class SongAdapter(
    private var songs: List<Song>,
    private val viewType: Int
): RecyclerView.Adapter<ViewHolder>() {
    var listener: Listener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Constants.VIEW_TYPE_VERTICAL -> SongVerticalViewHolder(
                LayoutSongVerticalItemBinding.inflate(inflater, parent, false)
            )
            Constants.VIEW_TYPE_HORIZONTAL -> SongHorizontalViewHolder(
                LayoutSongHorizontalItemBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Invalid View Type")
        }
    }
    override fun getItemCount(): Int = songs.size
    override fun getItemViewType(position: Int): Int = viewType
    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) =  when (holder) {
        is SongVerticalViewHolder -> holder.bind(songs[position])
        is SongHorizontalViewHolder -> holder.bind(songs[position])
        else -> throw IllegalArgumentException("Invalid View Holder")
    }
    fun setupSongs(songs: List<Song>) {
        val diffUtil = SongDiffUtil(this.songs, songs)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        this.songs = songs
    }
    inner class SongVerticalViewHolder(
        private val binding: LayoutSongVerticalItemBinding
    ): ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.run {
                tvSongTitle.text = song.title
                tvSongArtist.text = song.artist
                val image = File(song.imagePath)
                if(image.exists()) {
                    Glide.with(this.root)
                        .load(image)
                        .into(ivSongPic)
                }
                llSong.setOnClickListener { listener?.onClick(song) }
            }
        }
    }
    inner class SongHorizontalViewHolder(
        private val binding: LayoutSongHorizontalItemBinding
    ): ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.run {
                tvSongTitle.text = song.title
                tvSongArtist.text = song.artist
                val image = File(song.imagePath)
                if(image.exists()) {
                    Glide.with(this.root)
                        .load(image)
                        .into(ivSongPic)
                }
                llSong.setOnClickListener { listener?.onClick(song) }
            }
        }
    }
    interface Listener { fun onClick(song: Song) }
}

