package com.practicum.movies.presentation.details

import com.practicum.movies.domain.details.MovieDetails

sealed interface AboutState {

    data class Content(
        val movie: MovieDetails,
    ) : AboutState

    data class Error(
        val message: String,
    ) : AboutState

}