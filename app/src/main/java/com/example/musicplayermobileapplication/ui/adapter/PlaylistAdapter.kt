package com.example.musicplayermobileapplication.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.LayoutFavouritesItemBinding

class PlaylistAdapter(
    private var songs: List<Song>
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    var listener: Listener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = LayoutFavouritesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PlaylistViewHolder(binding)
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        return holder.bind(songs[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPlaylist(songs: List<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    inner class PlaylistViewHolder(
        private val binding: LayoutFavouritesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(songs: Song) {
            binding.tvSongTitle.text = songs.title
            binding.tvSongArtist.text = songs.artist
            Glide.with(binding.ivSongPic.context)
                .load(songs.filePath)
                .into(binding.ivSongPic)
        }
    }

    interface Listener {
        fun onClick(songs: Song)
    }
}

