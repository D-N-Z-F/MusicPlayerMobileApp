package com.example.musicplayermobileapplication.core.utils

import android.net.Uri
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayermobileapplication.data.model.Genders
import com.example.musicplayermobileapplication.data.model.Genres
import com.example.musicplayermobileapplication.data.model.Statuses
import java.io.File
import java.util.concurrent.TimeUnit

// Extension Functions to help shorten code as well as make it more readable.
fun File.isImage(): Boolean = Regex(Constants.IMAGE_REG).containsMatchIn(this.name)
fun File.isAudio(): Boolean = Regex(Constants.AUDIO_REG).containsMatchIn(this.name)
fun String.capitalize(): String = this.replaceFirstChar { it.uppercase() }
fun String.getDetailsFromAudioPath(): Pair<String, String> {
    val regex = Regex(".*/(.+?) - (.+?)\\.mp3")
    val result = regex.find(this)
    return Pair(
        result?.groups?.get(1)?.value ?: "",
        result?.groups?.get(2)?.value ?: ""
    )
}
fun Genres.format(): String = this.toString().lowercase().capitalize()
fun Genders.format(): String = this.toString().lowercase().capitalize()
fun Statuses.format(): String = this.toString().lowercase().capitalize()
fun Int.format(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this.toLong()) % 60
    return String.format("%02d:%02d", minutes, seconds)
}
fun Uri.isExternalStorageDocument(): Boolean
= "com.android.externalstorage.documents" == this.authority
fun Uri.isDownloadsDocument(): Boolean
= "com.android.providers.downloads.documents" == this.authority
fun Uri.isMediaDocument(): Boolean
= "com.android.providers.media.documents" == this.authority
