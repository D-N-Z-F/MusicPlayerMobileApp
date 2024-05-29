package com.example.musicplayermobileapplication.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.musicplayermobileapplication.core.constants.Constants
import com.example.musicplayermobileapplication.data.db.MusicPlayerDatabase
import com.example.musicplayermobileapplication.data.repository.repo.PlaylistRepo
import com.example.musicplayermobileapplication.data.repository.repo.SongRepo
import com.example.musicplayermobileapplication.data.repository.repo.UserRepo
import com.example.musicplayermobileapplication.data.repository.repoImpl.PlaylistRepoImpl
import com.example.musicplayermobileapplication.data.repository.repoImpl.SongRepoImpl
import com.example.musicplayermobileapplication.data.repository.repoImpl.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
        Room.databaseBuilder(context, MusicPlayerDatabase::class.java, MusicPlayerDatabase.NAME)
            .addMigrations(
                MusicPlayerDatabase.MIGRATION_1_2
            ).build()
    @Provides
    @Singleton
    fun provideUserRepo(db: MusicPlayerDatabase): UserRepo = UserRepoImpl(db.userDao())

    @Provides
    @Singleton
    fun providePlaylistRepo(db: MusicPlayerDatabase): PlaylistRepo = PlaylistRepoImpl(db.playlistDao())

    @Provides
    @Singleton
    fun provideSongRepo(db: MusicPlayerDatabase): SongRepo = SongRepoImpl(db.songDao())
}