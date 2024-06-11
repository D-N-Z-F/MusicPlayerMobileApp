package com.example.musicplayermobileapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.core.utils.Constants
import com.example.musicplayermobileapplication.core.utils.getDetailsFromAudioPath
import com.example.musicplayermobileapplication.core.utils.isAudio
import com.example.musicplayermobileapplication.core.utils.isImage
import com.example.musicplayermobileapplication.data.model.Genres
import com.example.musicplayermobileapplication.data.model.Song
import com.example.musicplayermobileapplication.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SharedViewModel by viewModels()
    private val songList: MutableList<Song> = mutableListOf()
    private val imageList: MutableList<String> = mutableListOf()
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if(!Environment.isExternalStorageManager()) {
            startActivity(Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.fromParts(Constants.PACKAGE, packageName, null)
            ))
        } else {
            val root = Environment.getExternalStorageDirectory()
            scanForAudioAndImageFiles(root)
            viewModel.firstTimeSetup(songList)
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
                                    genre = setGenres()
                                        .also { genres -> Log.d("setup_debugging", genres.toString()) },
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