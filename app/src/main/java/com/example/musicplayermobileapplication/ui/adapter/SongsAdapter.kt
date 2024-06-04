package com.example.musicplayermobileapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicplayermobileapplication.core.utils.PlaylistDiffUtil
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.databinding.LayoutHomePlaylistItemBinding
import java.io.File

class PlaylistAdapter(
    private var playlists: List<Playlist>
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {
    var listener: Listener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PlaylistViewHolder = PlaylistViewHolder(LayoutHomePlaylistItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ))
    override fun getItemCount() = playlists.size
    override fun onBindViewHolder(
        holder: PlaylistViewHolder, position: Int
    ) = holder.bind(playlists[position])
    fun setupPlaylists(playlists: List<Playlist>) {
        val diffUtil = PlaylistDiffUtil(this.playlists, playlists)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        this.playlists = playlists
    }
    inner class PlaylistViewHolder(
        private val binding: LayoutHomePlaylistItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.run {
                tvPlaylistTitle.text = playlist.title
                tvPlaylistDesc.text = playlist.desc
                val image = File(playlist.imagePath)
                if(image.exists()) {
                    Glide.with(this.root)
                        .load(image)
                        .into(ivPlaylistPic)
                }
                llPlaylist.setOnClickListener { listener?.onClick(playlist) }
            }
        }
    }
    interface Listener { fun onClick(playlist: Playlist) }
}
