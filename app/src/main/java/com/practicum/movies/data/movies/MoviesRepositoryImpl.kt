package com.practicum.movies.data.movies

import com.practicum.movies.data.cast.MovieCastRequest
import com.practicum.movies.data.cast.MovieCastResponse
import com.practicum.movies.data.cast.converters.MovieCastConverter
import com.practicum.movies.data.details.MovieDetailsRequest
import com.practicum.movies.data.details.MovieDetailsResponse
import com.practicum.movies.data.movies.converters.MovieDbConvertor
import com.practicum.movies.data.movies.db.AppDatabase
import com.practicum.movies.data.movies.dto.MovieDto
import com.practicum.movies.data.movies.dto.MoviesSearchRequest
import com.practicum.movies.data.movies.dto.MoviesSearchResponse
import com.practicum.movies.domain.cast.MovieCast
import com.practicum.movies.domain.details.MovieDetails
import com.practicum.movies.domain.movies.api.MoviesRepository
import com.practicum.movies.domain.movies.models.Movie
import com.practicum.movies.presentation.movies.LocalStorage
import com.practicum.movies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage,
    private val movieCastConverter: MovieCastConverter,
    private val appDatabase: AppDatabase,
    private val movieDbConvertor: MovieDbConvertor,
) : MoviesRepository {

    override fun searchMovies(expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val stored = localStorage.getSavedFavorites()
                with(response as MoviesSearchResponse) {
                    val data = results.map {
                        Movie(
                            it.id,
                            it.resultType,
                            it.image,
                            it.title,
                            it.description,
                            inFavorite = stored.toString().contains(it.id),
                        )
                    }
                    saveMovie(results)
                    emit(Resource.Success(data))
                }
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

    override fun getMovieDetails(movieId: String): Flow<Resource<MovieDetails>> = flow {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                with(response as MovieDetailsResponse) {
                    val data = MovieDetails(
                        id, title, imDbRating, year,
                        countries, genres, directors, writers, stars, plot
                    )
                    emit(Resource.Success(data))
                }
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override fun getMovieCast(movieId: String): Flow<Resource<MovieCast>> = flow {
        val response = networkClient.doRequest(MovieCastRequest(movieId))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                emit(
                    Resource.Success(
                        data = movieCastConverter.convert(response as MovieCastResponse)
                    )
                )
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    private suspend fun saveMovie(movies: List<MovieDto>) {
        val movieEntities = movies.map { movie -> movieDbConvertor.map(movie) }
        appDatabase.movieDao().insertMovies(movieEntities)
    }
}
