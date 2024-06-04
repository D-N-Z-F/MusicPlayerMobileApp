package com.example.musicplayermobileapplication.data.repository

import com.example.musicplayermobileapplication.data.db.SongDao
import com.example.musicplayermobileapplication.data.model.Song
import kotlinx.coroutines.flow.Flow

class SongRepoImpl(private val dao: SongDao): SongRepo {
    override fun addSong(song: Song) { dao.addSong(song) }
    override fun getAllSongs(): Flow<List<Song>> = dao.getAllSongs()
    override fun getSongById(id: Int): Song? = dao.getSongById(id)
    override fun updateSong(song: Song) { dao.updateSong(song) }
    override fun deleteSong(song: Song) { dao.deleteSong(song) }
}