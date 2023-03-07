package com.example.emagtest.repository

import com.example.emagtest.api.ApiService
import com.example.emagtest.data.NowPlayingPagingSource
import com.example.emagtest.models.MoviesDetailsModel
import com.example.emagtest.ui.movieDetails.MovieDetails
import dagger.Provides
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getMovieDetails(id: Int): Response<MoviesDetailsModel> {
        return apiService.getMovieDetails(id)
    }
}