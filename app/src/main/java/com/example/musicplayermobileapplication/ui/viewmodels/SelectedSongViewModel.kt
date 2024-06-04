package com.example.musicplayermobileapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.repository.repo.SongRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedSongViewModel @Inject constructor(
    val repo: SongRepo
) : ViewModel() {

    private val _song: MutableLiveData<Song> = MutableLiveData()
    val song: LiveData<Song> = _song

    fun getSongById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _song.postValue(repo.getSongById(id))
        }
    }
}