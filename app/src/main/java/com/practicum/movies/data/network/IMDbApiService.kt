package com.practicum.movies.data.network

import com.practicum.movies.data.dto.MoviesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {
    @GET("")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>
}