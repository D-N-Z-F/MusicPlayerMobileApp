package com.example.musicplayermobileapplication.data.repository.repoImpl

import com.example.musicplayermobileapplication.data.db.PlaylistDao
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.repository.repo.PlaylistRepo

class PlaylistRepoImpl(private val dao: PlaylistDao) : PlaylistRepo {
    override fun addPlaylist(playlist: Playlist) = dao.addPlaylist(playlist)

    override fun getAllUserPlaylists(userId: Int): List<Playlist> = dao.getAllUserPlaylists(userId)

    override fun getPlaylistById(id: Int): Playlist? = dao.getPlaylistById(id)

    override fun updatePlaylist(playlist: Playlist) = dao.updatePlaylist(playlist)

    override fun deletePlaylist(playlist: Playlist) = dao.deletePlaylist(playlist)

}