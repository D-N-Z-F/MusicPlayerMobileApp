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
    private val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish
    val showToast: MutableLiveData<String> = MutableLiveData()
    private var originalPlaylistData: Playlist? = null
    private fun triggerFinish() { viewModelScope.launch { _finish.emit(Unit) } }
    private fun getUserId() = authService.getUserId()
    fun getPlaylistById(): Flow<Playlist?> = playlistRepo.getPlaylistById(getUserId())
    fun setupOriginal(playlist: Playlist) { originalPlaylistData = playlist }
    fun validatePlaylist(type: String, title: String, desc: String) {
        if(type == "add") {
            if(title == "") {
                showToast.postValue("Please enter an appropriate title!")
            } else {
                addPlaylist(
                    Playlist(
                        userId = getUserId(),
                        title = title,
                        desc = desc,
                    )
                )
            }
        } else if(type == "edit") {
            showToast.postValue("EDIT'S WORKING??")
        }
    }
    private fun addPlaylist(playlist: Playlist) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                playlistRepo.addPlaylist(playlist)
                showToast.postValue("Added Successfully!")
                _finish.emit(Unit)
            }
        } catch (e: Exception) { showToast.postValue(e.message) }
    }
    private fun updatePlaylist(playlist: Playlist) {
        try {} catch (e: Exception) { showToast.postValue(e.message) }
    }
}