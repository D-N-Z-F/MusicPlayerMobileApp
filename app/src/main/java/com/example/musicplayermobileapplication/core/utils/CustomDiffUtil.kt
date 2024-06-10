package com.example.musicplayermobileapplication.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.Song

class SongDiffUtil(
    private val oldList: List<Song>,
    private val newList: List<Song>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ): Boolean = oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
}

class PlaylistDiffUtil(
    private val oldList: List<Playlist>,
    private val newList: List<Playlist>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ): Boolean = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(
        oldItemPosition: Int, newItemPosition: Int
    ): Boolean = oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
}