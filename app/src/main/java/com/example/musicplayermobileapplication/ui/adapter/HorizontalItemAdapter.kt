package com.example.musicplayermobileapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.core.utils.SongDiffUtil
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.databinding.LayoutSongHorizontalItemBinding
import java.io.File

class HorizontalItemAdapter(
    private var songs: List<Song>
): RecyclerView.Adapter<HorizontalItemAdapter.HorizontalViewHolder>() {
    var listener: Listener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalViewHolder = HorizontalViewHolder(LayoutSongHorizontalItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ))
    override fun getItemCount() = songs.size
    override fun onBindViewHolder(
        holder: HorizontalViewHolder, position: Int
    ) = holder.bind(songs[position])
    fun setupSongs(songs: List<Song>) {
        val diffUtil = SongDiffUtil(this.songs, songs)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        this.songs = songs
    }
    inner class HorizontalViewHolder(
        private val binding: LayoutSongHorizontalItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.run {
                tvTitle.text = song.title
                tvDesc.text = song.artist
                val image = File(song.imagePath)
                if(image.exists()) {
                    Glide.with(this.root)
                        .load(image)
                        .into(ivPic)
                }
                llHorizontal.setOnClickListener { listener?.onClick(song) }
            }
        }
    }
    interface Listener { fun onClick(song: Song) }
}