package com.example.musicplayermobileapplication.core.di

import android.content.Context
import androidx.room.Room
import com.example.musicplayermobileapplication.data.db.MusicPlayerDatabase
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
    fun provideRoomDB(@ApplicationContext context: Context): MusicPlayerDatabase =
        Room.databaseBuilder(context, MusicPlayerDatabase::class.java, MusicPlayerDatabase.NAME)
            .fallbackToDestructiveMigration().build()

//    @Provides
//    @Singleton
//    fun provideMusicPlayerRepo(db: MusicPlayerDatabase)
}