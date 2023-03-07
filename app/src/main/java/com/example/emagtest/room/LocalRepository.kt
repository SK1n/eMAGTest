package com.example.emagtest.room

import com.example.emagtest.models.MoviesModel
import javax.inject.Inject


class LocalRepository @Inject constructor(
    private val moviesDao: MoviesDao
){
    suspend fun insert(movie: MoviesModel) = moviesDao.insertMovie(movie)
    suspend fun delete(movie: MoviesModel) = moviesDao.deleteMovie(movie)
    suspend fun getMovies() = moviesDao.getMovies()
    suspend fun numberOfItemsInDB() = moviesDao.numberOfItemsInDB()
}