package com.example.musicplayermobileapplication.core.utils

import com.example.musicplayermobileapplication.data.model.Genders
import com.example.musicplayermobileapplication.data.model.Statuses
import java.io.File

// Extension Functions
fun File.isImage(): Boolean = Regex(Constants.IMAGE_REG).containsMatchIn(this.name)
fun File.isAudio(): Boolean = Regex(Constants.AUDIO_REG).containsMatchIn(this.name)
fun Genders.format(): String = this.toString().lowercase().replaceFirstChar { it.uppercase() }
fun Statuses.format(): String = this.toString().lowercase().replaceFirstChar { it.uppercase() }