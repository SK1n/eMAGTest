package com.example.emagtest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.emagtest.models.MoviesModel


@Database(entities = [MoviesModel::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao() : MoviesDao

}