package com.practicum.movies.domain.movies.db

import com.practicum.movies.domain.movies.models.Movie
import kotlinx.coroutines.flow.Flow


interface HistoryRepository {

    fun historyMovies(): Flow<List<Movie>>
}