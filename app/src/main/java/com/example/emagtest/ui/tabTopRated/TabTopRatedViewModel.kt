package com.example.emagtest.ui.tabTopRated

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.repository.PopularRepository
import com.example.emagtest.repository.TopRatedRepository
import kotlinx.coroutines.flow.Flow

class TabTopRatedViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<MoviesModel>> {
        return TopRatedRepository.getData()
    }
}