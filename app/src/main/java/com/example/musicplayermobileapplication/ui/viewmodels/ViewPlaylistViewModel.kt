package com.example.musicplayermobileapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.repository.PlaylistRepo
import com.example.musicplayermobileapplication.data.repository.SongRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ViewPlaylistViewModel @Inject constructor(
    private val playlistRepo: PlaylistRepo,
    private val songRepo: SongRepo
): ViewModel() {
    fun getPlaylistById(id: Int): Flow<Playlist?> = playlistRepo.getPlaylistById(id)
}