package com.example.emagtest.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.emagtest.api.ApiService
import com.example.emagtest.data.PopularPagingSource
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.utils.Constants
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getData(): Flow<PagingData<MoviesModel>> {
        return Pager(config = PagingConfig(
            pageSize = Constants.QUERY_PAGE_SIZE, enablePlaceholders = false
        ), pagingSourceFactory = { PopularPagingSource(apiService) }).flow
    }
}