package com.practicum.movies.data.movies

import android.util.Log
import com.practicum.movies.data.cast.MovieCastRequest
import com.practicum.movies.data.cast.MovieCastResponse
import com.practicum.movies.data.cast.converters.MovieCastConverter
import com.practicum.movies.data.details.MovieDetailsRequest
import com.practicum.movies.data.details.MovieDetailsResponse
import com.practicum.movies.data.movies.dto.MoviesSearchRequest
import com.practicum.movies.data.movies.dto.MoviesSearchResponse
import com.practicum.movies.domain.cast.MovieCast
import com.practicum.movies.domain.details.MovieDetails
import com.practicum.movies.domain.movies.api.MoviesRepository
import com.practicum.movies.domain.movies.models.Movie
import com.practicum.movies.presentation.movies.LocalStorage
import com.practicum.movies.util.Resource

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
) : MoviesRepository {

    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()
                with(response as MoviesSearchResponse) {
                    Resource.Success(results.map {
                        Movie(
                            it.id,
                            it.resultType,
                            it.image,
                            it.title,
                            it.description,
                            inFavorite = stored.toString().contains(it.id),
                        )
                    })
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        //Log.d("MyLog", response.toString())
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(
                        MovieDetails(
                            id, title, imDbRating, year,
                            countries, genres, directors, writers, stars, plot
                        )
                    )
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }

    override fun getMovieCast(movieId: String): Resource<MovieCast> {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        Log.d("MyLog", response.toString())
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success(
                    data = movieCastConverter.convert(response as MovieCastResponse)
                )
            }

            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }
}
