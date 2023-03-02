package com.example.emagtest.data.nowPlaying

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.emagtest.api.ApiService
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.utils.Constants.FIRST_PAGE
import com.example.emagtest.utils.Constants.QUERY_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

class PopularPagingSource(
    private val service: ApiService
) : PagingSource<Int, MoviesModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesModel> {
        val position = params.key ?: FIRST_PAGE
        return try {
            val response = service.getPopularMovies(page = position)
            val movies = response.body()?.results
            val nextKey = if (movies!!.isEmpty()) {
                null
            } else {
                position + (params.loadSize / QUERY_PAGE_SIZE)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == FIRST_PAGE) null else position,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, MoviesModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}