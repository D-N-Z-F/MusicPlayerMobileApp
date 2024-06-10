package com.example.musicplayermobileapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userId: Int,
    val favourites: List<Song> = emptyList(),
)
