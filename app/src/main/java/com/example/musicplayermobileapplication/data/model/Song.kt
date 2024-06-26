package com.example.musicplayermobileapplication.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val artist: String,
    val genre: List<Genres> = emptyList(),
    val imagePath: String = "/storage/emulated/0/Download/MusicIcon.jpg",
    val filePath: String
)

enum class Genres {
    RHYTHM_AND_BLUES,
    CLASSICAL,
    JAZZ,
    KOREAN,
    CHINESE,
    ENGLISH,
    POPULAR
}