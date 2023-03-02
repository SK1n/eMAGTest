package com.example.emagtest.models


import java.io.Serializable

data class MoviesPage(
    val page: Int,
    val results: MutableList<MoviesModel>,
    val total_pages: Int
)

data class MoviesModel(
    val title: String?,
    val id: Int?,
    val poster_path: String?,
    val original_title: String?,
    val vote_average: Float?,
    val popularity: Float?,
    val vote_count: Int?,
    val overview: String?,
    val release_date: String?,
    val backdrop_path: String?,
) : Serializable