package com.example.musicplayermobileapplication.core.utils

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayermobileapplication.data.model.Genders
import com.example.musicplayermobileapplication.data.model.Statuses
import java.io.File
import java.util.concurrent.TimeUnit

// Extension Functions
fun File.isImage(): Boolean = Regex(Constants.IMAGE_REG).containsMatchIn(this.name)
fun File.isAudio(): Boolean = Regex(Constants.AUDIO_REG).containsMatchIn(this.name)
fun String.capitalize(): String = this.replaceFirstChar { it.uppercase() }
fun Genders.format(): String = this.toString().lowercase().capitalize()
fun Statuses.format(): String = this.toString().lowercase().capitalize()
fun Int.format(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this.toLong())
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this.toLong()) % 60
    return String.format("%02d:%02d", minutes, seconds)
}
fun setDataVisibility(data: List<*>): Int = if(data.isEmpty()) View.GONE else View.VISIBLE
fun setPlaceholderVisibility(data: List<*>): Int = if(data.isNotEmpty()) View.GONE else View.VISIBLE
