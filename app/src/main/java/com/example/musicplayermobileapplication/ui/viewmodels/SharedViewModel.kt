package com.example.musicplayermobileapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.core.services.AuthService
import com.example.musicplayermobileapplication.data.model.Favourite
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
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val songRepo: SongRepo,
    private val playlistRepo: PlaylistRepo,
    private val favouriteRepo: FavouriteRepo,
    private val authService: AuthService
): ViewModel() {
    val showToast: MutableLiveData<String> = MutableLiveData()
    private val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish
    private fun getUserId(): Int = authService.getUserId()
    fun getUserById(): Flow<PublicUserDetails?> = userRepo.getUserById(getUserId())
    fun getAllSongs(): Flow<List<Song>> = songRepo.getAllSongs()
    fun getAllUserPlaylists(): Flow<List<Playlist>> = playlistRepo.getAllUserPlaylists(getUserId())
    fun getAllUserFavourites(): Flow<Favourite?> = favouriteRepo.getAllUserFavourites(getUserId())
    fun searchWord(query: String?, songList: List<Song>): List<Song> {
        return if (query.isNullOrBlank()) { songList }
        else { songList.filter { it.title.contains(query, ignoreCase = true) } }
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

    // Below All Temporary
//    private val songs = listOf(
//        Song(
//            title = "Flower",
//            artist = "Johnny Stimson",
//            genre = listOf(Genres.POPULAR, Genres.ENGLISH, Genres.RHYTHM_AND_BLUES),
//            imagePath = "/storage/emulated/0/Download/Image - Flower.jpg",
//            filePath = "/storage/emulated/0/Download/Johnny Stimson - Flower.mp3"
//        ),
//        Song(
//            title = "Still With You",
//            artist = "Jungkook",
//            genre = listOf(Genres.POPULAR, Genres.KOREAN, Genres.RHYTHM_AND_BLUES, Genres.JAZZ),
//            imagePath = "/storage/emulated/0/Download/Image - Still With You.jpg",
//            filePath = "/storage/emulated/0/Download/Jungkook - Still With You.mp3"
//        ),
//        Song(
//            title = "APPLE PIE",
//            artist = "Talitha.",
//            genre = listOf(Genres.ENGLISH, Genres.RHYTHM_AND_BLUES, Genres.POP),
//            imagePath = "/storage/emulated/0/Download/Image - APPLE PIE.jpg",
//            filePath = "/storage/emulated/0/Download/Talitha. - APPLE PIE.mp3"
//        ),
//        Song(
//            title = "Slow Dancing",
//            artist = "V",
//            genre = listOf(Genres.KOREAN, Genres.RHYTHM_AND_BLUES, Genres.JAZZ, Genres.POPULAR),
//            imagePath = "/storage/emulated/0/Download/Image - Slow Dancing.png",
//            filePath = "/storage/emulated/0/Download/V - Slow Dancing.mp3"
//        ),
//        Song(
//            title = "Comfortable",
//            artist = "Victor Ray",
//            genre = listOf(Genres.ENGLISH, Genres.RHYTHM_AND_BLUES, Genres.JAZZ, Genres.POP),
//            imagePath = "/storage/emulated/0/Download/Image - Comfortable.jpg",
//            filePath = "/storage/emulated/0/Download/Victor Ray - Comfortable.mp3"
//        ),
//        Song(
//            title = "就忘了吧",
//            artist = "1K",
//            genre = listOf(Genres.CHINESE, Genres.RHYTHM_AND_BLUES, Genres.EDM, Genres.POP),
//            imagePath = "/storage/emulated/0/Download/Image - 就忘了吧.jpg",
//            filePath = "/storage/emulated/0/Download/1K - 就忘了吧.mp3"
//        )
//    )
//    private val playlists = listOf(
//        Playlist(
//            userId = getUserId(),
//            title = "RNB",
//            desc = "Rhythm N Blues",
//            songs = listOf(
//                Song(
//                    title = "Flower",
//                    artist = "Johnny Stimson",
//                    genre = listOf(Genres.POPULAR, Genres.ENGLISH, Genres.RHYTHM_AND_BLUES),
//                    imagePath = "/storage/emulated/0/Download/Image - Flower.jpg",
//                    filePath = "/storage/emulated/0/Download/Johnny Stimson - Flower.mp3"
//                ),
//                Song(
//                    title = "Still With You",
//                    artist = "Jungkook",
//                    genre = listOf(Genres.POPULAR, Genres.KOREAN, Genres.RHYTHM_AND_BLUES, Genres.JAZZ),
//                    imagePath = "/storage/emulated/0/Download/Image - Still With You.jpg",
//                    filePath = "/storage/emulated/0/Download/Jungkook - Still With You.mp3"
//                )
//            )
//        ),
//        Playlist(
//            userId = getUserId(),
//            title = "Jazz",
//            desc = "Ya Like Jazz?",
//            songs = listOf(
//                Song(
//                    title = "Comfortable",
//                    artist = "Victor Ray",
//                    genre = listOf(Genres.ENGLISH, Genres.RHYTHM_AND_BLUES, Genres.JAZZ, Genres.POP),
//                    imagePath = "/storage/emulated/0/Download/Image - Comfortable.jpg",
//                    filePath = "/storage/emulated/0/Download/Victor Ray - Comfortable.mp3"
//                ),
//                Song(
//                    title = "Slow Dancing",
//                    artist = "V",
//                    genre = listOf(Genres.KOREAN, Genres.RHYTHM_AND_BLUES, Genres.JAZZ, Genres.POPULAR),
//                    imagePath = "/storage/emulated/0/Download/Image - Slow Dancing.png",
//                    filePath = "/storage/emulated/0/Download/V - Slow Dancing.mp3"
//                )
//            )
//        )
//    )
//    private val favourites = Favourite(
//        userId = getUserId(),
//        favourites = listOf(
//            Song(
//                title = "Flower",
//                artist = "Johnny Stimson",
//                genre = listOf(Genres.POPULAR, Genres.ENGLISH, Genres.RHYTHM_AND_BLUES),
//                imagePath = "/storage/emulated/0/Download/Image - Flower.jpg",
//                filePath = "/storage/emulated/0/Download/Johnny Stimson - Flower.mp3"
//            )
//        )
//    )
//    fun addEssentials() {
//        viewModelScope.launch(Dispatchers.IO) {
//            for(song in songs) {
//                songRepo.addSong(song)
//                Log.d("debugging", "${song.title} added!")
//            }
//            for(playlist in playlists) {
//                playlistRepo.addPlaylist(playlist)
//                Log.d("debugging", "${playlist.title} added!")
//            }
//            favouriteRepo.addFavourites(favourites)
//        }
//    }
}