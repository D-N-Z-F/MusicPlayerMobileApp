package com.example.musicplayermobileapplication.core.services

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class StorageService @Inject constructor(
    @ApplicationContext context: Context
){
    fun getFileFromPath(filePath: String): File = File(filePath)
}