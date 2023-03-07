package com.example.emagtest.ui.moviesNowPlaying

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.repository.NowPlayingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesNowPlayingViewModel @Inject constructor(private val repository: NowPlayingRepository) :
    ViewModel() {
    val movies: MutableLiveData<PagingData<MoviesModel>> = MutableLiveData()

    init {
        viewModelScope.launch {
            repository.getData().cachedIn(viewModelScope).collectLatest {
                movies.postValue(it)
            }
        }
    }
}