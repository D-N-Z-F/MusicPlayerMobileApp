package com.example.musicplayermobileapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val username: String,
    val password: Int,
    val age: String,
    val bio: String,
    val gender: Genders,
    val status: Statuses,
    val playlists: List<Playlist>,
    val favourites: List<Song>,
    val joinedAt: LocalDateTime = LocalDateTime.now()
)
enum class Genders { MALE, FEMALE, NON_BINARY, UNDISCLOSED }
enum class Statuses { NORMAL, PREMIUM }