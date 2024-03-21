package com.practicum.movies.presentation.history

import com.practicum.movies.domain.movies.models.Movie


sealed interface HistoryState {

    object Loading : HistoryState

    data class Content(
        val movies: List<Movie>,
    ) : HistoryState

    data class Empty(
        val message: String,
    ) : HistoryState
}