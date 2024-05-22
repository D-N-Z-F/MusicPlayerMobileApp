package com.example.musicplayermobileapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userId: Int,
    val title: String,
    val desc: String,
    val songs: List<Song>
)
