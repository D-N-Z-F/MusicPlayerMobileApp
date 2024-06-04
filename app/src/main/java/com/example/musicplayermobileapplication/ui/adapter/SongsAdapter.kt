package com.example.musicplayermobileapplication.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.LayoutFavouritesItemBinding

class SongsAdapter(
    private var songs: List<Song>
) : RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {

    var listener: Listener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val binding = LayoutFavouritesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SongsViewHolder(binding)
    }

    override fun getItemCount() = songs.size

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        return holder.bind(songs[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSongs(songs: List<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    inner class SongsViewHolder(
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

