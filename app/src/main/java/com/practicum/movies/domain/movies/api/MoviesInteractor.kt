package com.practicum.movies.domain.movies.api

import com.practicum.movies.domain.details.MovieDetails
import com.practicum.movies.domain.movies.models.Movie

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)
    fun getMoviesDetails(movieId: String, consumer: MovieDetailsConsumer)
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }
    interface MovieDetailsConsumer {
        fun consume(movieDetails: MovieDetails?, errorMessage: String?)
    }
}