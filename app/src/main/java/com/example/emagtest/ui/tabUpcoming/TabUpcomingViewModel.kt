package com.example.emagtest.ui.tabUpcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.repository.UpcomingRepository
import kotlinx.coroutines.flow.Flow

class TabUpcomingViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<MoviesModel>> {
        return UpcomingRepository.getData()
    }
}