package com.example.musicplayermobileapplication.core.converters

import androidx.room.TypeConverter
import com.example.musicplayermobileapplication.data.model.Genres
import com.example.musicplayermobileapplication.data.model.Playlist
import com.example.musicplayermobileapplication.data.model.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDateTime

// This class helps to convert types that are unrecognized by Room to a Json format for storage,
// and converts them back to their original types when retrieved by using Gson.
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun stringToDate(data: String?): LocalDateTime? = data?.let { LocalDateTime.parse(it) }
    @TypeConverter
    fun dateToString(data: LocalDateTime?): String? = data?.toString()
    @TypeConverter
    fun genresToString(data: List<Genres>?): String? = data?.let { gson.toJson(it) }
    @TypeConverter
    fun stringToGenres(data: String?): List<Genres>? {
        val listType: Type = object: TypeToken<List<Genres>>() {}.type
        return data?.let { gson.fromJson(it, listType) }
    }
    @TypeConverter
    fun stringToPlaylists(data: String?): List<Playlist>? {
        val listType: Type = object: TypeToken<List<Playlist>>() {}.type
        return data?.let { gson.fromJson(it, listType) }
    }
    @TypeConverter
    fun playlistsToString(data: List<Playlist>?): String? = data?.let { gson.toJson(it) }
    @TypeConverter
    fun stringToSongs(data: String?): List<Song>? {
        val listType: Type = object: TypeToken<List<Song>>() {}.type
        return data?.let { gson.fromJson(it, listType) }
    }
    @TypeConverter
    fun songsToString(data: List<Song>?): String? = data?.let { gson.toJson(it) }
}

