package com.example.emagtest.ui.tabSearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.emagtest.models.MoviesModel
import com.example.emagtest.repository.SearchMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabSearchMoviesViewModel @Inject constructor(
    private val repository: SearchMoviesRepository
) : ViewModel() {
    val movies: MutableLiveData<PagingData<MoviesModel>> = MutableLiveData()

    fun getSearchResults(query: String) = viewModelScope.launch {
        repository.getData(query).cachedIn(viewModelScope).collectLatest {
            movies.postValue(it)
        }
    }
}