package com.example.musicplayermobileapplication.data.repository
import com.example.musicplayermobileapplication.data.model.Song
import kotlinx.coroutines.flow.Flow

interface SongRepo {
    fun addSong(song: Song)
    fun getAllSongs(): Flow<List<Song>>
    fun getSongById(id: Int): Song?
    fun updateSong(song: Song)
    fun deleteSong(song: Song)
}