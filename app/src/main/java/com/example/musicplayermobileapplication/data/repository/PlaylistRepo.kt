package com.example.musicplayermobileapplication.data.repository
import com.example.musicplayermobileapplication.data.model.Playlist

interface PlaylistRepo {
    fun addPlaylist(playlist: Playlist)
    fun getAllUserPlaylists(userId: Int): List<Playlist>
    fun getPlaylistById(id: Int): Playlist?
    fun updatePlaylist(playlist: Playlist)
    fun deletePlaylist(playlist: Playlist)
}