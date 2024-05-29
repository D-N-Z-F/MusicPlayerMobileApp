package com.example.musicplayermobileapplication.data.repository
import com.example.musicplayermobileapplication.data.model.Song

interface SongRepo {
    fun addSong(song: Song)
    fun getAllSongs(): List<Song>
    fun getSongById(id: Int): Song?
    fun updateSong(song: Song)
    fun deleteSong(song: Song)
}