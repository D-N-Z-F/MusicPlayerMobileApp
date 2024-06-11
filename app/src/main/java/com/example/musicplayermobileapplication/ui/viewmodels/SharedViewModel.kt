package com.example.musicplayermobileapplication.ui.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.core.services.AuthService
import com.example.musicplayermobileapplication.core.services.StorageService
import com.example.musicplayermobileapplication.core.utils.getDetailsFromAudioPath
import com.example.musicplayermobileapplication.core.utils.isAudio
import com.example.musicplayermobileapplication.core.utils.isImage
import com.example.musicplayermobileapplication.data.model.Favourite
import com.example.musicplayermobileapplication.data.model.Genres
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.PublicUserDetails
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.model.User
import com.example.musicplayermobileapplication.data.repository.FavouriteRepo
import com.example.musicplayermobileapplication.data.repository.PlaylistRepo
import com.example.musicplayermobileapplication.data.repository.SongRepo
import com.example.musicplayermobileapplication.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val songRepo: SongRepo,
    private val playlistRepo: PlaylistRepo,
    private val favouriteRepo: FavouriteRepo,
    private val authService: AuthService,
    private val storageService: StorageService
): ViewModel() {
    val showToast: MutableLiveData<String> = MutableLiveData()
    private val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish

    private fun getUserId(): Int = authService.getUserId()
    fun getUserById(): Flow<PublicUserDetails?> = userRepo.getUserById(getUserId())
    fun getAllSongs(): Flow<List<Song>> = songRepo.getAllSongs()
    fun getAllUserPlaylists(): Flow<List<Playlist>> = playlistRepo.getAllUserPlaylists(getUserId())
    fun getAllUserFavourites(): Flow<Favourite?> = favouriteRepo.getAllUserFavourites(getUserId())
    fun searchWord(query: String?, genre: Genres?, songList: List<Song>): List<Song> {
        return songList.filter {
            val queryFilter = query.isNullOrBlank() || it.title.contains(query, ignoreCase = true)
            val genreFilter = genre == null || it.genre.contains(genre)
            queryFilter && genreFilter
        }.also {
            Log.d("debugging", it.toString())
        }
    }
    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            authService.logout()
            showToast.postValue("Logout Successful!")
            _finish.emit(Unit)
        }
    }
    fun isLoggedIn(): Boolean = authService.isLoggedIn()
    @RequiresApi(Build.VERSION_CODES.R)
    fun checkExternalStoragePermissions() { storageService.checkExternalStoragePermissions() }
}