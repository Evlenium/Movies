package com.practicum.movies.domain.movies.api

import com.practicum.movies.domain.cast.MovieCast
import com.practicum.movies.domain.details.MovieDetails
import com.practicum.movies.domain.movies.models.Movie
import com.practicum.movies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun searchMovies(expression: String): Flow<Resource<List<Movie>>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>>
    fun getMovieCast(movieId: String): Flow<Resource<MovieCast>>
}