package com.example.musicplayermobileapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.repository.repo.SongRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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