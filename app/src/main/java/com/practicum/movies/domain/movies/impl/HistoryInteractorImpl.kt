package com.practicum.movies.domain.movies.impl

import com.practicum.movies.domain.movies.db.HistoryInteractor
import com.practicum.movies.domain.movies.db.HistoryRepository
import com.practicum.movies.domain.movies.models.Movie
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl(private val historyRepository: HistoryRepository) : HistoryInteractor {
    override fun historyMovies(): Flow<List<Movie>> {
        return historyRepository.historyMovies()
    }
}