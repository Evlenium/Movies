package com.practicum.movies.data.movies.network

import com.practicum.movies.data.cast.MovieCastResponse
import com.practicum.movies.data.details.MovieDetailsResponse
import com.practicum.movies.data.movies.dto.MoviesSearchResponse
import com.practicum.movies.data.persons.dto.NamesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("/en/API/SearchMovie//{expression}")
    suspend fun searchMovies(@Path("expression") expression: String): MoviesSearchResponse

    @GET("/en/API/Title//{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetailsResponse

    @GET("/en/API/FullCast//{movie_id}")
    suspend fun getFullCast(@Path("movie_id") movieId: String): MovieCastResponse

    @GET("/en/API/SearchName//{expression}")
    suspend fun searchNames(@Path("expression") expression: String): NamesSearchResponse

}