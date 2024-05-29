package com.example.musicplayermobileapplication.core.utils

import java.io.File

// Extension Functions
fun File.isImage(): Boolean = Regex(Constants.IMAGE_REG).containsMatchIn(this.name)
fun File.isAudio(): Boolean = Regex(Constants.AUDIO_REG).containsMatchIn(this.name)