package com.practicum.movies.data.movies

import com.practicum.movies.data.movies.converters.MovieDbConvertor
import com.practicum.movies.data.movies.db.AppDatabase
import com.practicum.movies.data.movies.db.entity.MovieEntity
import com.practicum.movies.domain.movies.db.HistoryRepository
import com.practicum.movies.domain.movies.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val movieDbConvertor: MovieDbConvertor,
) : HistoryRepository {
    override fun historyMovies(): Flow<List<Movie>> = flow {
        val movies = appDatabase.movieDao().getMovies()
        emit(convertFromMovieEntity(movies))
    }

    private fun convertFromMovieEntity(movies: List<MovieEntity>): List<Movie> {
        return movies.map { movie -> movieDbConvertor.map(movie) }
    }
}