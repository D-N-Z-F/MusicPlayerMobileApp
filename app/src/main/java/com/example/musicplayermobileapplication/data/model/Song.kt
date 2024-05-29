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
    val imagePath: String = "",
    val filePath: String
)
enum class Genres {
    RHYTHM_AND_BLUES, CLASSICAL, JAZZ, POP, HIP_HOP, ROCK, COUNTRY, EDM, KOREAN, JAPANESE,
    CHINESE, ENGLISH, POPULAR
}