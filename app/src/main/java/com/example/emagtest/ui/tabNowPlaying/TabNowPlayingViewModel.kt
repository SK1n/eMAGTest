package com.example.emagtest.ui.tabNowPlaying

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.repository.NowPlayingRepository
import kotlinx.coroutines.flow.Flow

class TabNowPlayingViewModel : ViewModel() {
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun getData(): Flow<PagingData<MoviesModel>> {
        return NowPlayingRepository.getData()
    }
}