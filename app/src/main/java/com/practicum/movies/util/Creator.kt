package com.practicum.movies.util

import com.practicum.movies.presentation.details.PosterPresenter
import com.practicum.movies.presentation.details.PosterView

object Creator {
    fun providePosterPresenter(
        posterView: PosterView,
        imageUrl: String,
    ): PosterPresenter {
        return PosterPresenter(posterView, imageUrl)
    }
}