package com.example.emagtest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.emagtest.api.RetrofitInstance
import com.example.emagtest.data.NowPlayingPagingSource
import com.example.emagtest.data.UpcomingPagingSource
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.utils.Constants
import kotlinx.coroutines.flow.Flow

object UpcomingRepository {
    fun getData(): Flow<PagingData<MoviesModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.QUERY_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UpcomingPagingSource(RetrofitInstance.api) }
        ).flow
    }
}