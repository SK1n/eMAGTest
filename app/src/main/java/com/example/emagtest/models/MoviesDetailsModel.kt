package com.example.emagtest.models

data class MoviesDetailsModel(
    val backdrop_path: String?,
    val genres: List<Genres>,
    val id: Int,
    val title: String,
    val overview: String?,
    val poster_path: String?,
    val release_date: String,
    val tagline: String,
    val vote_average: Number,
    val vote_count: Int,
) {
    data class Genres(
        val id: Int,
        val name: String,
    )
}