package com.example.musicplayermobileapplication.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.musicplayermobileapplication.R
import com.example.musicplayermobileapplication.core.utils.Constants
import com.example.musicplayermobileapplication.core.utils.isAudio
import com.example.musicplayermobileapplication.core.utils.isImage
import com.example.musicplayermobileapplication.data.repository.SongRepo
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
        }
    }
    //Temporary
    private fun scanForAudioAndImageFiles(root: File) {
        if(root.isDirectory) {
            root.listFiles()?.let {
                for(each in it) {
                    if(each.isDirectory) { scanForAudioAndImageFiles(each) }
                    else if(each.isImage() || each.isAudio()) { Log.d("file_path_debugging", each.path) }
                }
            }
        }
    }
}