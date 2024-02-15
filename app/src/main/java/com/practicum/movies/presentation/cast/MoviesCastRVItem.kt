package com.practicum.movies.presentation.cast

import com.practicum.movies.domain.cast.MovieCastPerson

sealed interface MoviesCastRVItem {

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem

}