package com.example.emagtest.ui.tabPopular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.repository.PopularRepository
import kotlinx.coroutines.flow.Flow

class TabPopularViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<MoviesModel>> {
        return PopularRepository.getData()
    }
}