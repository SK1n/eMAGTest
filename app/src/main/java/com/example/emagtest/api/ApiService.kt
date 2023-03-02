package com.example.emagtest.api

import com.example.emagtest.BuildConfig
import com.example.emagtest.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MoviesPage>
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MoviesPage>
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MoviesPage>
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") api: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MoviesPage>
}
