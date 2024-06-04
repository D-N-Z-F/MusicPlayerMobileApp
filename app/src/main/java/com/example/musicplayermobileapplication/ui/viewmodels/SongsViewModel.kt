package com.example.musicplayermobileapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.repository.SongRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(
    val repo: SongRepo
) : ViewModel() {

    private val _song: MutableLiveData<List<Song>> = MutableLiveData()
    val song: LiveData<List<Song>> = _song

//    fun getAllSongs() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _song.postValue(repo.getAllSongs())
//        }
//    }
//
//    fun getSongById(id: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _song.postValue(repo.getSongById(id))
//        }
//    }
}