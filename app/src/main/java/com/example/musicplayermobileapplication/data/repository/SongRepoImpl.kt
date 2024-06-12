package com.example.musicplayermobileapplication.data.repository

import com.example.musicplayermobileapplication.data.db.SongDao
import com.example.musicplayermobileapplication.data.model.Song
import kotlinx.coroutines.flow.Flow

class SongRepoImpl(private val dao: SongDao): SongRepo {
    override fun addSong(song: Song) { dao.addSong(song) }
    override fun validateSong(title: String, artist: String): Song?
    = dao.validateSong(title, artist)
    override fun getAllSongs(): Flow<List<Song>> = dao.getAllSongs()
    override fun getAllSongsInstance(): List<Song> = dao.getAllSongsInstance()
    override fun getSongById(id: Int): Flow<Song?> = dao.getSongById(id)
    override fun updateSong(song: Song) { dao.updateSong(song) }
    override fun deleteSong(song: Song) { dao.deleteSong(song) }
}