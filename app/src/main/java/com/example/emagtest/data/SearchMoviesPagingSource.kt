package com.example.emagtest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.emagtest.api.ApiService
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.utils.Constants
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class SearchMoviesPagingSource @Inject constructor(
    private val service: ApiService,
    private val query: String,
) : PagingSource<Int, MoviesModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesModel> {
        val position = params.key ?: Constants.FIRST_PAGE
        return try {
            val response = service.getSearchResults(page = position, query = query)
            val movies = response.body()?.results
            val nextKey = if (movies!!.isEmpty()) {
                null
            } else {
                position + (params.loadSize / Constants.QUERY_PAGE_SIZE)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (position == Constants.FIRST_PAGE) null else position,
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