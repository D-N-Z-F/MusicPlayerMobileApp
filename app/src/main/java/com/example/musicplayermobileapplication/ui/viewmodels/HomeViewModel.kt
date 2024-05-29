package com.example.musicplayermobileapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.data.model.Genres
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.repository.SongRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val songRepo: SongRepo
): ViewModel() {
//    private val songs: List<Song> = listOf(
//        Song(
//            title = "Flower",
//            artist = "Johnny Stimson",
//            genre = listOf(
//                Genres.POPULAR,
//                Genres.ENGLISH,
//                Genres.RHYTHM_AND_BLUES
//            ),
//            imagePath = "/storage/emulated/0/Download/Image - Flower.jpg",
//            filePath = "/storage/emulated/0/Download/Johnny Stimson - Flower.mp3"
//        ),
//        Song(
//            title = "Still With You",
//            artist = "Jungkook",
//            genre = listOf(
//                Genres.POPULAR,
//                Genres.KOREAN,
//                Genres.RHYTHM_AND_BLUES,
//                Genres.JAZZ
//            ),
//            imagePath = "/storage/emulated/0/Download/Image - Still With You.jpg",
//            filePath = "/storage/emulated/0/Download/Jungkook - Still With You.mp3"
//        ),
//        Song(
//            title = "APPLE PIE",
//            artist = "Talitha.",
//            genre = listOf(
//                Genres.ENGLISH,
//                Genres.RHYTHM_AND_BLUES,
//                Genres.POP
//            ),
//            imagePath = "/storage/emulated/0/Download/Image - APPLE PIE.jpg",
//            filePath = "/storage/emulated/0/Download/Talitha. - APPLE PIE.mp3"
//        ),
//        Song(
//            title = "Slow Dancing",
//            artist = "V",
//            genre = listOf(
//                Genres.POPULAR,
//                Genres.KOREAN,
//                Genres.RHYTHM_AND_BLUES,
//                Genres.JAZZ
//            ),
//            imagePath = "/storage/emulated/0/Download/Image - Slow Dancing.png",
//            filePath = "/storage/emulated/0/Download/V - Slow Dancing.mp3"
//        ),
//        Song(
//            title = "Comfortable",
//            artist = "Victor Ray",
//            genre = listOf(
//                Genres.ENGLISH,
//                Genres.RHYTHM_AND_BLUES,
//                Genres.JAZZ
//            ),
//            imagePath = "/storage/emulated/0/Download/Image - Comfortable.jpg",
//            filePath = "/storage/emulated/0/Download/Victor Ray - Comfortable.mp3"
//        ),
//        Song(
//            title = "Flower",
//            artist = "Johnny Stimson",
//            genre = listOf(
//                Genres.POPULAR,
//                Genres.ENGLISH,
//                Genres.RHYTHM_AND_BLUES
//            ),
//            imagePath = "/storage/emulated/0/Download/Image - Flower.jpg",
//            filePath = "/storage/emulated/0/Download/Johnny Stimson - Flower.mp3"
//        )
//    )
//    init {
//        for(song in songs) {
//            viewModelScope.launch(Dispatchers.IO) { songRepo.addSong(song) }
//            Log.d("debugging", "${song.title} added successfully!")
//        }
//    }
}