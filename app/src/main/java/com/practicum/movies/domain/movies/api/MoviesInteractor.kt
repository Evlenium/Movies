package com.practicum.movies.domain.movies.api

import com.practicum.movies.domain.cast.MovieCast
import com.practicum.movies.domain.details.MovieDetails
import com.practicum.movies.domain.movies.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesInteractor {
    fun searchMovies(expression: String): Flow<Pair<List<Movie>?, String?>>
    fun getMoviesDetails(movieId: String): Flow<Pair<MovieDetails?, String?>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun getMoviesCast(movieId: String): Flow<Pair<MovieCast?, String?>>
}