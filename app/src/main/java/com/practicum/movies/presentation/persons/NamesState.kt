package com.practicum.movies.presentation.persons

import com.practicum.movies.domain.persons.models.Person

sealed interface NamesState {

    object Loading : NamesState

    data class Content(
        val persons: List<Person>,
    ) : NamesState

    data class Error(
        val message: String,
    ) : NamesState

    data class Empty(
        val message: String,
    ) : NamesState

}