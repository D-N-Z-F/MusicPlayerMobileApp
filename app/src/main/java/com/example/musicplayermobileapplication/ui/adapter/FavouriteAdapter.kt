package com.example.musicplayermobileapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.core.utils.SongDiffUtil
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.LayoutSongVerticalItemBinding
import com.example.musicplayermobileapplication.databinding.LayoutSongHorizontalItemBinding
import java.io.File

class FavouriteAdapter(
    private var songs: List<Song>,
    private val viewType: Int
): RecyclerView.Adapter<ViewHolder>() {
    var listener: Listener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_GRID -> FavouriteHomeViewHolder(
                LayoutSongVerticalItemBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_LINEAR -> FavouriteLibraryViewHolder(
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
        is FavouriteHomeViewHolder -> holder.bind(songs[position])
        is FavouriteLibraryViewHolder -> holder.bind(songs[position])
        else -> throw IllegalArgumentException("Invalid View Holder")
    }
    fun setupSongs(songs: List<Song>) {
        val diffUtil = SongDiffUtil(this.songs, songs)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        this.songs = songs
    }
    inner class FavouriteHomeViewHolder(
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
    inner class FavouriteLibraryViewHolder(
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
    companion object {
        const val VIEW_TYPE_GRID = 1
        const val VIEW_TYPE_LINEAR = 2
    }
}