package com.practicum.movies.data.movies.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.practicum.movies.data.cast.MovieCastRequest
import com.practicum.movies.data.details.MovieDetailsRequest
import com.practicum.movies.data.movies.NetworkClient
import com.practicum.movies.data.movies.dto.MoviesSearchRequest
import com.practicum.movies.data.movies.dto.Response
import com.practicum.movies.data.persons.dto.NamesSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(private val imdbService: IMDbApiService, private val context: Context) :
    NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        if ((dto !is MoviesSearchRequest) && (dto !is MovieDetailsRequest) && (dto !is MovieCastRequest) && (dto !is NamesSearchRequest)) {
            return Response().apply { resultCode = 400 }
        }
        when (dto) {
            is MoviesSearchRequest -> {
                return withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.searchMovies(dto.expression)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            is MovieDetailsRequest -> {
                return withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.getMovieDetails(dto.movieId)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            is NamesSearchRequest -> {
                return withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.searchNames(dto.expression)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }

            else -> {
                return withContext(Dispatchers.IO) {
                    try {
                        val response = imdbService.getFullCast((dto as MovieCastRequest).movieId)
                        response.apply { resultCode = 200 }
                    } catch (e: Throwable) {
                        Response().apply { resultCode = 500 }
                    }
                }
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}