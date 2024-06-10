package com.example.musicplayermobileapplication.core.utils

import com.example.musicplayermobileapplication.data.model.Genders
import com.example.musicplayermobileapplication.data.model.Statuses
import java.io.File

// Extension Functions
fun File.isImage(): Boolean = Regex(Constants.IMAGE_REG).containsMatchIn(this.name)
fun File.isAudio(): Boolean = Regex(Constants.AUDIO_REG).containsMatchIn(this.name)
fun String.capitalize(): String = this.replaceFirstChar { it.uppercase() }
fun Genders.format(): String = this.toString().lowercase().capitalize()
fun Statuses.format(): String = this.toString().lowercase().capitalize()