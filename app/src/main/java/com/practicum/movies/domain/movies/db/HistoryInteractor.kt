package com.practicum.movies.domain.movies.db

import com.practicum.movies.domain.movies.models.Movie
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {

    fun historyMovies(): Flow<List<Movie>>
}