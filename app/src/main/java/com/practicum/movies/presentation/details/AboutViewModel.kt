package com.practicum.movies.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.movies.domain.details.MovieDetails
import com.practicum.movies.domain.movies.api.MoviesInteractor
import kotlinx.coroutines.launch

class AboutViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLiveData

    init {
        viewModelScope.launch {
            moviesInteractor
                .getMoviesDetails(movieId)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }

    }

    private fun processResult(movieDetails: MovieDetails?, errorMessage: String?) {
        if (movieDetails != null) {
            stateLiveData.postValue(AboutState.Content(movieDetails))
        } else {
            stateLiveData.postValue(AboutState.Error(errorMessage ?: "Unknown error"))
        }
    }
}