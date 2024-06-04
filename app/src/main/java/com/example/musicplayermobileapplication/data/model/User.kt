package com.example.musicplayermobileapplication.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val username: String,
    val password: Int,
    val age: Int,
    val bio: String = "",
    val gender: Genders,
    val imagePath: String = "",
    val status: Statuses = Statuses.NORMAL,
    val joinedAt: LocalDateTime = LocalDateTime.now()
)
enum class Genders { MALE, FEMALE, NON_BINARY, UNDISCLOSED }
enum class Statuses { NORMAL, PREMIUM }