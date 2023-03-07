package com.example.emagtest.ui.moviesNowPlaying

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.repository.NowPlayingRepository
import com.example.emagtest.room.LocalRepository
import com.example.emagtest.room.MoviesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesNowPlayingViewModel @Inject constructor(
    private val repository: NowPlayingRepository, private val localRepository: LocalRepository
) : ViewModel() {
    val movies: MutableLiveData<PagingData<MoviesModel>> = MutableLiveData()

    init {
        viewModelScope.launch {
            repository.getData().cachedIn(viewModelScope).collectLatest {
                movies.postValue(it)
            }
        }
    }

    suspend fun getMovies(): List<MoviesModel> {
        return localRepository.getMovies()
    }

    suspend fun addToDb(movie: MoviesModel) = localRepository.insert(movie)

    suspend fun getDbSize() = localRepository.numberOfItemsInDB()


    suspend fun removeFromDb(movie: MoviesModel) = localRepository.delete(movie)
}