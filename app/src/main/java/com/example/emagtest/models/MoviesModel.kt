package com.example.emagtest.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movieEntity")
data class MoviesModel(
    @ColumnInfo(name = "title") val title: String?,
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "poster_path") val poster_path: String?,
    @ColumnInfo(name = "original_title") val original_title: String?,
    @ColumnInfo(name = "vote_average") val vote_average: Float?,
    @ColumnInfo(name = "popularity") val popularity: Float?,
    @ColumnInfo(name = "vote_count") val vote_count: Int?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "release_date") val release_date: String?,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String?,
    val isFavorite : Boolean = false,
) : Serializable