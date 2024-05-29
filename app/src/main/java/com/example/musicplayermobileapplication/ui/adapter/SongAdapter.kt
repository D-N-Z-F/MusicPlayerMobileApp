package com.example.musicplayermobileapplication.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.LayoutHomeSongItemBinding

class SongAdapter(
    private var songs: List<Song>
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    val listener: Listener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = LayoutHomeSongItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SongViewHolder(binding)
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        return holder.bind(songs[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPlaylist(songs: List<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    inner class SongViewHolder(
        private val binding: LayoutHomeSongItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(songs: Song) {
            binding.tvSongTitle.text = songs.title
            binding.tvSongArtist.text = songs.artist
        }
    }

    interface Listener {
        fun onClick(songs: Song)
    }
}

