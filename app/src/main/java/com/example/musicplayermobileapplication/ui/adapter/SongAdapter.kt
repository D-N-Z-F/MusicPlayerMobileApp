package com.example.musicplayermobileapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.core.utils.SongDiffUtil
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.LayoutHomeSongItemBinding
import java.io.File

class SongAdapter(
    private var songs: List<Song>
): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    var listener: Listener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SongViewHolder = SongViewHolder(LayoutHomeSongItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ))
    override fun getItemCount() = songs.size
    override fun onBindViewHolder(
        holder: SongViewHolder, position: Int
    ) = holder.bind(songs[position])
    fun getSongs() = this.songs
    fun setupSongs(songs: List<Song>) {
        val diffUtil = SongDiffUtil(this.songs, songs)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        this.songs = songs
    }
    inner class SongViewHolder(
        private val binding: LayoutHomeSongItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
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

