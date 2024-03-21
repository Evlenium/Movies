package com.practicum.movies.presentation.cast

import com.practicum.movies.domain.cast.MovieCastPerson
import com.practicum.movies.ui.cast.core.RVItem

sealed interface MoviesCastRVItem : RVItem {

    data class HeaderItem(
        val headerText: String,
    ) : MoviesCastRVItem

    data class PersonItem(
        val data: MovieCastPerson,
    ) : MoviesCastRVItem

}