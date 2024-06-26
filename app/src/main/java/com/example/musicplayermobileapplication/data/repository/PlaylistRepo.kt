package com.example.musicplayermobileapplication.data.repository
import com.example.musicplayermobileapplication.data.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepo {
    fun addPlaylist(playlist: Playlist)
    fun getAllUserPlaylists(userId: Int): Flow<List<Playlist>>
    fun getAllUserPlaylistsInstance(userId: Int): List<Playlist>
    fun getPlaylistById(id: Int): Flow<Playlist?>
    fun getPlaylistByIdInstance(id: Int): Playlist?
    fun updatePlaylist(playlist: Playlist)
    fun deletePlaylist(playlist: Playlist)
}