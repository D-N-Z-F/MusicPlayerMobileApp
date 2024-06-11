package com.example.musicplayermobileapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.core.services.AuthService
import com.example.musicplayermobileapplication.data.model.Favourite
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.repository.FavouriteRepo
import com.example.musicplayermobileapplication.data.repository.PlaylistRepo
import com.example.musicplayermobileapplication.data.repository.SongRepo
import com.example.musicplayermobileapplication.data.repository.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewSongViewModel @Inject constructor(
    private val favouriteRepo: FavouriteRepo,
    private val songRepo: SongRepo,
    private val playlistRepo: PlaylistRepo,
    private val authService: AuthService
) : ViewModel() {
    val showToast: MutableLiveData<String> = MutableLiveData()
    private var song: Song? = null
    private var favourite: Favourite? = null
    private fun getUserId(): Int = authService.getUserId()
    fun setSong(song: Song) { this.song = song }
    fun setFavourite(favourite: Favourite) { this.favourite = favourite }
    fun getSongById(id: Int): Flow<Song?> = songRepo.getSongById(id)
    fun getAllUserFavourites(): Flow<Favourite?> = favouriteRepo.getAllUserFavourites(getUserId())
    fun getAllUserPlaylists(): Flow<List<Playlist>> = playlistRepo.getAllUserPlaylists(getUserId())
    fun addRemoveFromPlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            val songs = playlist.songs.toMutableList()
            val existingSong = songs.find { it.id == song?.id }
            if(existingSong == null) { songs.add(song!!) }
            else { songs.remove(song!!) }
            playlistRepo.updatePlaylist(playlist.copy(songs = songs.toList()))
            showToast.postValue(
                "${if(existingSong == null) "Added" else "Removed"} Successfully!"
            )
        }
    }
    fun isFavourited(): Boolean {
        favourite?.let {
            val favourites = it.favourites
            val list = favourites.filter { each -> each.id == song?.id }
            Log.d("debugging", list.toString())
            return list.isNotEmpty()
        }
        return false
    }
    fun addRemoveFavourite(song: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            if(favourite != null) {
                val newList: MutableList<Song> = favourite!!.favourites.toMutableList()
                if(isFavourited()) { newList.remove(song) }
                else { newList.add(song) }
                favouriteRepo.updateFavourites(favourite!!.copy(favourites = newList))
            } else {
                favouriteRepo.addFavourites(
                    Favourite(
                        userId = getUserId(),
                        favourites = listOf(song)
                    )
                )
            }
            delay(200)
            showToast.postValue(if(isFavourited()) "You liked this!" else "You unliked this!")
        }
    }
}