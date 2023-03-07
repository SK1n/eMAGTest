package com.example.emagtest.di

import android.content.Context
import androidx.room.Room
import com.example.emagtest.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Singleton
    @Provides
    fun moviesDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "movies-list.db").fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun moviesDao(db: AppDatabase) = db.moviesDao()
}