package com.example.musicplayermobileapplication.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.musicplayermobileapplication.core.utils.Constants
import com.example.musicplayermobileapplication.data.db.MusicPlayerDatabase
import com.example.musicplayermobileapplication.data.repository.FavouriteRepo
import com.example.musicplayermobileapplication.data.repository.FavouriteRepoImpl
import com.example.musicplayermobileapplication.data.repository.PlaylistRepo
import com.example.musicplayermobileapplication.data.repository.PlaylistRepoImpl
import com.example.musicplayermobileapplication.data.repository.SongRepo
import com.example.musicplayermobileapplication.data.repository.SongRepoImpl
import com.example.musicplayermobileapplication.data.repository.UserRepo
import com.example.musicplayermobileapplication.data.repository.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// By utilizing Hilt, this class stores the essential app modules to help reduce boilerplate
// code and helps keep things organized. Mainly used to inject dependencies when needed.
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.AUTH_PREFS, Context.MODE_PRIVATE)
    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context): MusicPlayerDatabase =
        Room.databaseBuilder(
            context, MusicPlayerDatabase::class.java, MusicPlayerDatabase.NAME
        ).fallbackToDestructiveMigration().build()
    @Provides
    @Singleton
    fun provideUserRepo(db: MusicPlayerDatabase): UserRepo = UserRepoImpl(db.userDao())
    @Provides
    @Singleton
    fun provideSongRepo(db: MusicPlayerDatabase): SongRepo = SongRepoImpl(db.songDao())
    @Provides
    @Singleton
    fun providePlaylistRepo(db: MusicPlayerDatabase): PlaylistRepo =
        PlaylistRepoImpl(db.playlistDao())
    @Provides
    @Singleton
    fun provideFavouriteRepo(db: MusicPlayerDatabase): FavouriteRepo =
        FavouriteRepoImpl(db.favouriteDao())
}