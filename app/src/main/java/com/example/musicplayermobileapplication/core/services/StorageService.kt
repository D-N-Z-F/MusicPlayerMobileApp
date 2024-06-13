package com.example.musicplayermobileapplication.core.services

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.musicplayermobileapplication.core.utils.Constants
import com.example.musicplayermobileapplication.core.utils.getDetailsFromAudioPath
import com.example.musicplayermobileapplication.core.utils.isAudio
import com.example.musicplayermobileapplication.core.utils.isImage
import com.example.musicplayermobileapplication.data.model.Genres
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.data.repository.SongRepo
import com.example.musicplayermobileapplication.ui.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

// This class acts as a service class that provides storage access methods throughout the app.
class StorageService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val songRepo: SongRepo
){
    private val songList: MutableList<Song> = mutableListOf()
    private val imageList: MutableList<String> = mutableListOf()
    @RequiresApi(Build.VERSION_CODES.R)
    fun checkExternalStoragePermissions() {
        context.run {
            if(!Environment.isExternalStorageManager()) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.fromParts(Constants.PACKAGE, packageName, null)
                    )
                )
            } else {
                val root = Environment.getExternalStorageDirectory()
                scanForAudioAndImageFiles(root)
                firstTimeSetup(songList)
            }
        }
    }
    private fun scanForAudioAndImageFiles(root: File) {
        if(root.isDirectory) {
            root.listFiles()?.let {
                for(each in it) {
                    if(each.isDirectory) { scanForAudioAndImageFiles(each) }
                    else {
                        if(each.isImage() && !imageList.contains(each.path)) {
                            imageList.add(each.path)
                        } else if(each.isAudio() && !isDuplicate(each)) {
                            val (artist, title) = each.path.getDetailsFromAudioPath()
                            songList.add(
                                Song(
                                    title = title,
                                    artist = artist,
                                    genre = setGenres().also { genres ->
                                        Log.d("debugging", genres.toString()) },
                                    imagePath = setImagePath(title),
                                    filePath = each.path
                                )
                            )
                            Log.d("setup_debugging", songList.toString())
                        }
                    }
                }
            }
        }
    }
    private fun firstTimeSetup(songs: List<Song>) {
        CoroutineScope(Dispatchers.IO + Job()).launch {
            for(song in songs) {
                val existingSong = songRepo.validateSong(song.title, song.artist)
                if(existingSong == null) {
                    songRepo.addSong(song)
                    Log.d("setup_debugging", "${song.title} added!")
                } else {
                    Log.d("setup_debugging", "${song.title} already exists!")
                }
            }
            Job().complete()
        }
    }
    private fun isDuplicate(file: File): Boolean {
        val (artist, title) = file.path.getDetailsFromAudioPath()
        Log.d("setup_debugging", artist)
        Log.d("setup_debugging", title)
        for(song in songList) {
            if(song.title == title) { return true }
        }
        return false
    }
    private fun setGenres(): List<Genres> {
        val genres = Genres.entries
        val rand = (1 .. 5).random()
        val set = mutableSetOf<Genres>()
        while(set.size < rand) { set.add(genres.random()) }
        return set.toList()
    }
    private fun setImagePath(title: String): String {
        for(image in imageList) {
            if(image.contains(title)) return image
        }
        return "/storage/emulated/0/Download/MusicIcon.jpg"
    }
}