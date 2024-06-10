package com.example.musicplayermobileapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.core.services.AuthService
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.repository.PlaylistRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPlaylistViewModel @Inject constructor(
    private val playlistRepo: PlaylistRepo,
    private val authService: AuthService
): ViewModel() {
    private val _finish: MutableSharedFlow<Byte> = MutableSharedFlow()
    val finish: SharedFlow<Byte> = _finish
    val showToast: MutableLiveData<String> = MutableLiveData()
    private var originalPlaylistData: Playlist? = null
    private fun triggerFinish(value: Byte) { viewModelScope.launch { _finish.emit(value) } }
    private fun getUserId() = authService.getUserId()
    fun getPlaylistById(id: Int): Flow<Playlist?> = playlistRepo.getPlaylistById(id)
    fun setupOriginal(playlist: Playlist) { originalPlaylistData = playlist }
    fun validatePlaylist(type: String, title: String, desc: String) {
        if(title == "") {
            showToast.postValue("Please enter an appropriate title!")
            return
        }
        if(type == "add") {
            addPlaylist(Playlist(userId = getUserId(), title = title, desc = desc))
        } else if(type == "edit") {
            originalPlaylistData?.let {
                if(title == it.title && desc == it.desc) {
                    showToast.postValue("Nothing to change!")
                } else {
                    updatePlaylist(it.copy(title = title, desc = desc))
                }
            }
        }
    }
    private fun addPlaylist(playlist: Playlist) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                playlistRepo.addPlaylist(playlist)
                showToast.postValue("Added Successfully!")
            }
        } catch (e: Exception) { showToast.postValue(e.message) }
        triggerFinish(0)
    }
    private fun updatePlaylist(playlist: Playlist) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                playlistRepo.updatePlaylist(playlist)
                showToast.postValue("Updated Successfully!")
            }
        } catch (e: Exception) { showToast.postValue(e.message) }
        triggerFinish(0)
    }
    fun deletePlaylist() {
        try {
            originalPlaylistData?.let {
                viewModelScope.launch(Dispatchers.IO) {
                    playlistRepo.deletePlaylist(it)
                    showToast.postValue("Deleted Successfully!")
                }
            }
        } catch (e: Exception) { showToast.postValue(e.message) }
        triggerFinish(1)
    }
}