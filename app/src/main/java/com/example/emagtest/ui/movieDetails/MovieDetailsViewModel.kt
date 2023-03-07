package com.example.emagtest.ui.movieDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emagtest.models.MoviesDetailsModel
import com.example.emagtest.repository.MovieDetailsRepository
import com.example.emagtest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val state: SavedStateHandle, private val repository: MovieDetailsRepository
) : ViewModel() {
    val movieData: MutableLiveData<Resource<MoviesDetailsModel>> = MutableLiveData()
    var pageResponse: MoviesDetailsModel? = null


    init {
        val id = state.get<Int>("id")!!
        viewModelScope.launch {
            val response = repository.getMovieDetails(id)
            movieData.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<MoviesDetailsModel>): Resource<MoviesDetailsModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(pageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}