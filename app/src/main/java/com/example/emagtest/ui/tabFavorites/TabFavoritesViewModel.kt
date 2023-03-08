package com.example.emagtest.ui.tabFavorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.room.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TabFavoritesViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {
    val movies : MutableLiveData<List<MoviesModel>> = MutableLiveData()
    fun getMovies() {
        viewModelScope.launch {
            movies.postValue(localRepository.getMovies())
        }
    }
}

