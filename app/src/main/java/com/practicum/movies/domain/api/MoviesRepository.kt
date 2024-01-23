package com.practicum.movies.domain.api

import com.practicum.movies.domain.models.Movie
import com.practicum.movies.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}