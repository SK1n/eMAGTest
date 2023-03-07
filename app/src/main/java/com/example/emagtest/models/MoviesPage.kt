package com.example.emagtest.models

data class MoviesPage(
    val page: Int, val results: MutableList<MoviesModel>, val total_pages: Int
)