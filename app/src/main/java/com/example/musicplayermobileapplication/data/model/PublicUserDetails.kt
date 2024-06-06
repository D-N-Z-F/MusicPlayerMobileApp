package com.example.musicplayermobileapplication.data.model

import java.time.LocalDateTime

data class PublicUserDetails(
    val id: Int,
    val username: String,
    val age: Int,
    val bio: String = "",
    val gender: Genders,
    val imagePath: String = "/storage/emulated/0/Download/MONICA.jpg",
    val status: Statuses = Statuses.NORMAL,
    val joinedAt: LocalDateTime = LocalDateTime.now()
)
